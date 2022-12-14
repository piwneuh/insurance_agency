import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Brand } from '../../model/brand';
import { Car } from '../../model/car';
import { Model } from '../../model/model';
import { Proposal } from '../../model/proposal';
import { BrandService } from '../../services/brand.service';
import { ProposalService } from '../../services/proposal.service';

@Component({
  selector: 'app-add-car-to-proposal',
  templateUrl: './add-car-to-proposal.component.html',
  styleUrls: ['./add-car-to-proposal.component.css'],
})
export class AddCarToProposalComponent implements OnInit {
  @Input() proposal: Proposal;
  @Output() completedEvent = new EventEmitter<Proposal>();
  completed: boolean = false;

  car: Car = { model: { years: new Array() } as Model } as Car;

  brands: Array<Brand>;
  selectedBrand: Brand;
  models: Array<Model>;

  constructor(
    private brandService: BrandService,
    private proposalService: ProposalService,
    private snackbar: MatSnackBar
  ) {}

  ngOnInit(): void {
    if (this.proposal?.car) {
      this.car = this.proposal.car;
      this.completed = true;
      return;
    }
    this.getBrands();
  }

  getBrands() {
    this.brandService.getAllBrands().subscribe((resp) => {
      this.brands = resp;
      this.models = {} as Array<Model>;
      this.car.model = { years: new Array() } as Model;
      this.car.year = {} as number;
    });
  }

  getModels() {
    this.brandService.getAllModels(this.selectedBrand.id).subscribe((resp) => {
      this.models = resp;
      this.car.model = { years: new Array() } as Model;
      this.car.year = {} as number;
    });
  }

  addCar() {
    this.proposalService
      .addCarToProposal(this.proposal.id, this.car)
      .subscribe({
      next:(resp) => {
        this.completed = true;
        this.completedEvent.emit(resp);
      },
      error: () => this.snackbar.open('Please, fill out all the fields.', 'Ok', {panelClass:['blue-snackbar']}),
      complete: () => this.snackbar.open('Successfully added!', 'Ok', {panelClass:['blue-snackbar']})
      });
  }
}
