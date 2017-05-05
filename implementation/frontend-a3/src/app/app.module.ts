import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { BootstrapModalModule } from 'ng2-bootstrap-modal';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { UserListComponent } from './user/user-list/user-list.component';
import { UserAddComponent } from './user/user-add/user-add.component';
import { UserDetailComponent } from './user/user-detail/user-detail.component';
import { UserFormComponent } from './user/user-form/user-form.component';

import { HttpService } from './service/http.service';

import { ConfirmComponent } from './dialogs/confirm/confirm.component';
import { InfoComponent } from './dialogs/info/info.component';
import { InputComponent } from './dialogs/input/input.component';
import { ScheduleComponent } from './user/schedule/schedule.component';
import { PatientListComponent } from './patient/patient-list/patient-list.component';
import { PatientAddComponent } from './patient/patient-add/patient-add.component';
import { PatientFormComponent } from './patient/patient-form/patient-form.component';
import { PatientDetailComponent } from './patient/patient-detail/patient-detail.component';

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
    PatientDetailComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,
    BootstrapModalModule
  ],
  providers: [
    HttpService
  ],
  entryComponents: [
    ConfirmComponent,
    InfoComponent,
    InputComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }