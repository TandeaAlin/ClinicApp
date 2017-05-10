import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { BootstrapModalModule } from 'ng2-bootstrap-modal';
import { NguiDatetimePickerModule } from '@ngui/datetime-picker';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UserAddComponent } from './user/user-add/user-add.component';
import { UserDetailComponent } from './user/user-detail/user-detail.component';
import { UserFormComponent } from './user/user-form/user-form.component';

import { HttpService } from './service/http.service';
import { AuthService } from './service/auth.service';

import { LoginComponent } from './login/login.component';

import { ConfirmComponent } from './dialogs/confirm/confirm.component';
import { InfoComponent } from './dialogs/info/info.component';
import { InputComponent } from './dialogs/input/input.component';
import { ScheduleComponent } from './user/schedule/schedule.component';
import { PatientListComponent } from './patient/patient-list/patient-list.component';
import { PatientAddComponent } from './patient/patient-add/patient-add.component';
import { PatientFormComponent } from './patient/patient-form/patient-form.component';
import { PatientDetailComponent } from './patient/patient-detail/patient-detail.component';
import { ConsultationListComponent } from './consultation/consultation-list/consultation-list.component';
import { ConsultationAddComponent } from './consultation/consultation-add/consultation-add.component';
import { ConsultationFormComponent } from './consultation/consultation-form/consultation-form.component';
import { ConsultationDetailComponent } from './consultation/consultation-detail/consultation-detail.component';
import { ObservationsComponent } from './consultation/observations/observations.component';
import { UpcomingConsultationListComponent } from './consultation/upcoming-consultation-list/upcoming-consultation-list.component';
import { PatientConsultationListComponent } from './consultation/patient-consultation-list/patient-consultation-list.component';
import { NotificationListComponent } from './notification-list/notification-list.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ConfirmComponent,
    InfoComponent,
    InputComponent,
    UserListComponent,
    UserAddComponent,
    UserDetailComponent,
    UserFormComponent,
    ScheduleComponent,
    PatientListComponent,
    PatientAddComponent,
    PatientFormComponent,
    PatientDetailComponent,
    ConsultationListComponent,
    ConsultationAddComponent,
    ConsultationFormComponent,
    ConsultationDetailComponent,
    LoginComponent,
    ObservationsComponent,
    UpcomingConsultationListComponent,
    PatientConsultationListComponent,
    NotificationListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    BootstrapModalModule,
    NguiDatetimePickerModule
  ],
  providers: [
    HttpService,
    AuthService
  ],
  entryComponents: [
    ConfirmComponent,
    InfoComponent,
    InputComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
