import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { faCalendar, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { Driver } from '../../model/driver.model';
import { Proposal } from '../../model/proposal';
import { Risk } from '../../model/risk.model';
import { CountryService } from '../../services/country.service';
import { DriverService } from '../../services/driver.service';
import { ProposalService } from '../../services/proposal.service';

@Component({
  selector: 'app-add-drivers-to-proposal',
  templateUrl: './add-drivers-to-proposal.component.html',
  styleUrls: ['./add-drivers-to-proposal.component.css'],
})
export class AddDriversToProposalComponent implements OnInit {
  @Input() proposal: Proposal;
  @Output() completedEvent = new EventEmitter<Proposal>();

  completed: boolean = false;

  countries: Array<any>;
  risks: Array<Risk>;
  drivers: Array<Driver> = new Array();

  subscriberChecked: boolean = false;

  faCalender = faCalendar;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(
    private driverService: DriverService,
    private countryService: CountryService,
    private proposalService: ProposalService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (this.proposal?.drivers.length > 0) {
      this.completed = true;
      this.drivers = this.proposal.drivers;
      return;
    }
    this.getCountries();
    this.getRisks();
  }

  addSubscriber(add: boolean) {
    if (add) {
      let driver = new Driver();
      driver.jmbg = this.proposal.subscriber.jmbg;
      this.drivers.push(driver);
      this.subscriberChecked = true;
    } else {
      this.subscriberChecked = true;
    }
  }

  getCountries() {
    this.countryService.getAllCountries().subscribe((resp) => {
      this.countries = resp;
    });
  }

  getRisks() {
    this.driverService.getAllRisks().subscribe((resp) => (this.risks = resp));
  }

  getProps(driver: Driver): any {
    return {
      driver,
      countries: this.countries,
      risks: this.risks,
      completed: this.completed,
    };
  }

  addNewDriver() {
    if (this.drivers.length < 4) {
      this.drivers.push(new Driver());
    }
  }

  removeDriver(index: number) {
    this.drivers.splice(index, 1);
  }

  addDriversToProposal() {
    this.proposalService
      .addDriversToProposal(this.proposal.id, this.drivers)
      .subscribe({next: (resp) => {
        this.completedEvent.emit(resp);
        this.completed = true;
      },
      error: () => this.snackbar.open('Please, fill out all the fields.', 'Ok', {panelClass:['blue-snackbar']}),
      complete: () => this.snackbar.open('Successfully added!', 'Ok', {panelClass:['blue-snackbar']})
     });
  }
}
