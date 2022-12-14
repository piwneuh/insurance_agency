import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddCarToProposalComponent } from './proposal-components/add-car-to-proposal/add-car-to-proposal.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AddSubscriberToProposalComponent } from './proposal-components/add-subscriber-to-proposal/add-subscriber-to-proposal.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AddDriversToProposalComponent } from './proposal-components/add-drivers-to-proposal/add-drivers-to-proposal.component';
import { AddDriverComponent } from './proposal-components/add-driver/add-driver.component';
import { AddInsurancePlanToProposalComponent } from './proposal-components/add-insurance-plan-to-proposal/add-insurance-plan-to-proposal.component';
import { ProposalPageComponent } from './proposal-page/proposal-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatStepperModule } from '@angular/material/stepper';
import { LoginComponent } from './login/login.component';
import { TokenInterceptor } from './interceptors/token.interceptor';
import { ManageBrandsComponent } from './admin-components/manage-brands/manage-brands.component';
import { ManageModelsComponent } from './admin-components/manage-models/manage-models.component';
import { ManageCountriesComponent } from './admin-components/manage-countries/manage-countries.component';
import { ManageRisksComponent } from './admin-components/manage-risks/manage-risks.component';
import { ManageInsuranceItemsComponent } from './admin-components/manage-insurance-items/manage-insurance-items.component';
import { ManageCitiesComponent } from './admin-components/manage-cities/manage-cities.component';
import { ManageUsersComponent } from './admin-components/manage-users/manage-users.component';
import { ManageInsurancePlansComponent } from './admin-components/manage-insurance-plans/manage-insurance-plans.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MAT_SNACK_BAR_DEFAULT_OPTIONS } from '@angular/material/snack-bar';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { AuthGuard } from './services/auth-guard.service';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { AddPolicyToProposalComponent } from './proposal-components/add-policy-to-proposal/add-policy-to-proposal.component';
import { ChequePaymentComponent } from './proposal-components/payement/cheque-payment/cheque-payment.component';
import { CardPaymentComponent } from './proposal-components/payement/card-payment/card-payment.component';
import { BankPaymentComponent } from './proposal-components/payement/bank-payment/bank-payment.component';
import { AgentDashboardComponent } from './agent-dashboard/agent-dashboard.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { ProposalViewComponent } from './proposal-views/proposal-view/proposal-view.component';
import { SubscriberViewComponent } from './proposal-views/subscriber-view/subscriber-view.component';
import { CarViewComponent } from './proposal-views/car-view/car-view.component';
import { DriverViewComponent } from './proposal-views/driver-view/driver-view.component';
import { MatChipsModule } from '@angular/material/chips';
import { InsurancePlanViewComponent } from './proposal-views/insurance-plan-view/insurance-plan-view.component';
import { PolicyViewComponent } from './proposal-views/policy-view/policy-view.component';
import { ChequePaymentViewComponent } from './proposal-views/cheque-payment-view/cheque-payment-view.component';
import { CardPaymentViewComponent } from './proposal-views/card-payment-view/card-payment-view.component';
import { BankPaymentViewComponent } from './proposal-views/bank-payment-view/bank-payment-view.component';
import { SearchProposalComponent } from './search-proposal/search-proposal.component';
import { ProposalListComponent } from './proposal-list/proposal-list.component';

@NgModule({
  declarations: [
    AppComponent,
    AddCarToProposalComponent,
    AddSubscriberToProposalComponent,
    AddDriversToProposalComponent,
    AddDriverComponent,
    AddInsurancePlanToProposalComponent,
    ProposalPageComponent,
    LoginComponent,
    ManageBrandsComponent,
    ManageModelsComponent,
    ManageCountriesComponent,
    ManageRisksComponent,
    ManageInsuranceItemsComponent,
    ManageCitiesComponent,
    ManageUsersComponent,
    ManageInsurancePlansComponent,
    AdminDashboardComponent,
    NavBarComponent,
    AddPolicyToProposalComponent,
    ChequePaymentComponent,
    CardPaymentComponent,
    BankPaymentComponent,
    AgentDashboardComponent,
    ChangePasswordComponent,
    ProposalViewComponent,
    SubscriberViewComponent,
    CarViewComponent,
    DriverViewComponent,
    InsurancePlanViewComponent,
    PolicyViewComponent,
    ChequePaymentViewComponent,
    CardPaymentViewComponent,
    BankPaymentViewComponent,
    SearchProposalComponent,
    ProposalListComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule,
    FontAwesomeModule,
    BrowserAnimationsModule,
    MatStepperModule,
    NgMultiSelectDropDownModule.forRoot(),
    MatSnackBarModule,
    MatChipsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: { duration: 2500 } },
    AuthGuard,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
