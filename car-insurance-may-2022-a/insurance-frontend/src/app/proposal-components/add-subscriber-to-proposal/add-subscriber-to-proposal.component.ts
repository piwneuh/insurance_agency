import { Component, Input, OnInit } from '@angular/core';
import { faCalendar } from '@fortawesome/free-solid-svg-icons';
import { Person } from '../../model/person.model';
import { NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { Output, EventEmitter } from '@angular/core';
import { SubscriberService } from '../../services/subscriber.service';
import { CountryService } from '../../services/country.service';
import { ProposalService } from '../../services/proposal.service';
import { Proposal } from '../../model/proposal';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-subscriber-to-proposal',
  templateUrl: './add-subscriber-to-proposal.component.html',
  styleUrls: ['./add-subscriber-to-proposal.component.css'],
})
export class AddSubscriberToProposalComponent implements OnInit {
  @Input() proposal: Proposal;
  @Output() completedEvent = new EventEmitter<Proposal>();
  completed: boolean = false;

  known: any = true;
  checked: any = false;

  countries: any;
  selectedCountry: any;
  cities: any;
  selectedCity: any;

  subscriber: Person = new Person();

  faCalendar = faCalendar;

  today = new Date();
  maxDate = {
    year: this.today.getFullYear() - 18,
    month: this.today.getMonth() + 1,
    day: this.today.getDate(),
  };

  constructor(
    private subscriberService: SubscriberService,
    private countryService: CountryService,
    private proposalService: ProposalService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (this.proposal?.subscriber) {
      this.subscriber = this.proposal.subscriber;
      this.completed = true;
      return;
    }
    this.getCountries();
  }

  checkJmbg() {
    let jmbg = this.subscriber.jmbg;
    this.subscriber = new Person();
    this.subscriber.jmbg = jmbg;
    this.subscriberService.checkJmbg(this.subscriber.jmbg).subscribe(
      (resp) => {
        this.subscriber = { ...resp };
        this.checked = true;
      },
      (err) => {
        if (err.status === 404) {
          this.known = false;
          this.checked = true;
        } else {
          console.log(err);
        }
      }
    );
  }

  getCountries() {
    this.countryService.getAllCountries().subscribe((resp) => {
      this.countries = resp;
      this.cities = null;
    });
  }

  getCities() {
    this.countryService
      .getAllCities(this.selectedCountry.id)
      .subscribe((resp) => (this.cities = resp));
  }

  dateSelected(date: NgbDate) {
    let year = date.year.toString();
    let month =
      date.month.toString().length === 1
        ? `0${date.month.toString()}`
        : date.month.toString();
    let day =
      date.day.toString().length === 1
        ? `0${date.day.toString()}`
        : date.day.toString();

    this.subscriber.birth = `${year}-${month}-${day} 00:00`;
  }

  addSubscriber() {
    this.proposalService.addSubscriber(this.subscriber).subscribe({
      next: (resp) => {
        localStorage.setItem('proposalId', resp.id.toString());
        this.completedEvent.emit(resp);
        this.completed = true;
      },
      error: () =>
        this.snackbar.open('Please, fill out all the fields.', 'Ok', {
          panelClass: ['blue-snackbar'],
        }),
      complete: () =>
        this.snackbar.open('Successfully added!', 'Ok', {
          panelClass: ['blue-snackbar'],
        }),
    });
  }
}
