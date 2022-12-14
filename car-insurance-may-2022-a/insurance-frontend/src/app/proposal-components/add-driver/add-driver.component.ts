import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Driver } from '../../model/driver.model';
import { faCalendar, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { NgbDate, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DriverService } from '../../services/driver.service';
import { Risk } from '../../model/risk.model';
import { Accident } from '../../model/accident.model';
import { SubscriberService } from '../../services/subscriber.service';
import { CountryService } from '../../services/country.service';

@Component({
  selector: 'app-add-driver',
  templateUrl: './add-driver.component.html',
  styleUrls: ['./add-driver.component.css'],
})
export class AddDriverComponent implements OnInit {
  @Input() props: {
    countries: Array<any>;
    risks: Array<Risk>;
    driver: Driver;
    completed: boolean;
  };

  existingRisks: Array<Risk> = [];
  existingAccidents: Array<Accident>[];
  checked: boolean = false;
  known: boolean = false;
  isSubscriber: boolean = false;

  faCalendar = faCalendar;
  faPlus = faPlus;
  faTrash = faTrash;

  accident: Accident = new Accident();

  selectedCountry: any;
  cities: Array<any>;
  today = new Date();
  maxDateBirth = {
    year: this.today.getFullYear() - 18,
    month: this.today.getMonth() + 1,
    day: this.today.getDate(),
  };
  maxDateAccident = {
    year: this.today.getFullYear(),
    month: this.today.getMonth() + 1,
    day: this.today.getDate(),
  };

  constructor(
    private subscriberService: SubscriberService,
    private driverService: DriverService,
    private countryService: CountryService,
    private modalService: NgbModal
  ) {}

  ngOnInit(): void {
    if (this.props.driver.jmbg) {
      this.driverService.checkJmbg(this.props.driver.jmbg).subscribe(
        (resp) => {
          this.props.driver.firstName = resp.firstName;
          this.props.driver.lastName = resp.lastName;
          this.props.driver.maritalStatus = resp.maritalStatus;
          this.props.driver.gender = resp.gender;
          this.props.driver.birth = resp.birth;
          this.props.driver.contact = resp.contact;
          this.props.driver.address = resp.address;
          this.props.driver.licenceNum = resp.licenceNum;
          this.props.driver.licenceObtained = resp.licenceObtained;
          this.props.driver.yearsInsured = resp.yearsInsured;
          this.props.driver.risks = resp.risks;
          this.props.driver.accidentHistories = resp.accidentHistories;

          this.checked = true;
          this.known = true;
          this.existingRisks = JSON.parse(JSON.stringify(resp.risks));
          this.existingAccidents = JSON.parse(
            JSON.stringify(resp.accidentHistories)
          );
        },
        (err) => {
          if (err.status === 404) {
            this.checkSubscriberJmbg();
          }
        }
      );
    }
  }

  getCities() {
    this.countryService
      .getAllCities(this.selectedCountry.id)
      .subscribe((data) => {
        this.cities = data;
      });
  }

  formatDate(date: NgbDate): string {
    let year = date.year.toString();
    let month =
      date.month.toString().length === 1
        ? `0${date.month.toString()}`
        : date.month.toString();
    let day =
      date.day.toString().length === 1
        ? `0${date.day.toString()}`
        : date.day.toString();

    return `${year}-${month}-${day} 00:00`;
  }

  birthSelected(date: NgbDate) {
    this.props.driver.birth = this.formatDate(date);
    console.log(this.props.driver.birth);
  }

  checkSubscriberJmbg() {
    this.subscriberService
      .checkJmbg(this.props.driver.jmbg)
      .subscribe((resp) => {
        this.props.driver.firstName = resp.firstName;
        this.props.driver.lastName = resp.lastName;
        this.props.driver.maritalStatus = resp.maritalStatus;
        this.props.driver.gender = resp.gender;
        this.props.driver.birth = resp.birth;
        this.props.driver.contact = resp.contact;
        this.props.driver.address = resp.address;
        this.props.driver.risks = new Array<Risk>();

        this.checked = true;
        this.known = true;
        this.isSubscriber = true;
      });
  }

  checkDriverJmbg() {
    this.driverService.checkJmbg(this.props.driver.jmbg).subscribe(
      (resp) => {
        this.props.driver.firstName = resp.firstName;
        this.props.driver.lastName = resp.lastName;
        this.props.driver.maritalStatus = resp.maritalStatus;
        this.props.driver.gender = resp.gender;
        this.props.driver.birth = resp.birth;
        this.props.driver.contact = resp.contact;
        this.props.driver.address = resp.address;
        this.props.driver.licenceNum = resp.licenceNum;
        this.props.driver.licenceObtained = resp.licenceObtained;
        this.props.driver.risks = resp.risks;
        this.props.driver.accidentHistories = resp.accidentHistories;

        this.existingRisks = JSON.parse(JSON.stringify(resp.risks));
        this.existingAccidents = JSON.parse(
          JSON.stringify(resp.accidentHistories)
        );
        this.checked = true;
        this.known = true;
      },
      (err) => {
        if (err.status === 404) {
          this.checked = true;
        }
      }
    );
  }

  open(content: any) {
    this.modalService.open(content, {
      ariaLabelledBy: 'modal-basic-title',
      size: 'lg',
    });
  }

  isRiskChecked(risk: Risk): boolean {
    return !!this.props.driver.risks.find((r) => r.id === risk.id);
  }

  isRiskDisabled(risk: Risk): boolean {
    return !!this.existingRisks.find((r) => r.id === risk.id);
  }

  addRisk(event: any, risk: Risk) {
    if (event.target.checked) {
      this.props.driver.risks.push(risk);
    } else {
      this.props.driver.risks.splice(
        this.props.driver.risks.findIndex((r) => r.id === risk.id),
        1
      );
    }
  }

  addAccident() {
    this.props.driver.accidentHistories.push(this.accident);
    this.accident = new Accident();
  }

  removeAccident(index: number) {
    this.props.driver.accidentHistories.splice(index, 1);
  }

  happenedSelected(date: NgbDate) {
    this.accident.happened = this.formatDate(date);
  }

  licenceObtainedSelected(date: NgbDate) {
    this.props.driver.licenceObtained = this.formatDate(date);
  }
}
