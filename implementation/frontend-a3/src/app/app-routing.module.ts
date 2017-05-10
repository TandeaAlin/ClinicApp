import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from './login/login.component';
import { NotificationListComponent } from './notification-list/notification-list.component';

import { UserListComponent } from './user/user-list/user-list.component';
import { UserAddComponent } from './user/user-add/user-add.component';
import { UserDetailComponent } from './user/user-detail/user-detail.component';
import { ScheduleComponent } from './user/schedule/schedule.component';

import { PatientListComponent } from './patient/patient-list/patient-list.component';
import { PatientAddComponent } from './patient/patient-add/patient-add.component';
import { PatientDetailComponent } from './patient/patient-detail/patient-detail.component';

import { ConsultationListComponent } from './consultation/consultation-list/consultation-list.component';
import { ConsultationAddComponent } from './consultation/consultation-add/consultation-add.component';
import { ConsultationDetailComponent } from './consultation/consultation-detail/consultation-detail.component';
import { ObservationsComponent } from './consultation/observations/observations.component';
import { UpcomingConsultationListComponent } from './consultation/upcoming-consultation-list/upcoming-consultation-list.component';
import { PatientConsultationListComponent } from './consultation/patient-consultation-list/patient-consultation-list.component';

const routes: Routes = [
  {
    path: '',
    children: []
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'notification-list',
    component: NotificationListComponent
  },
  {
    path: 'user',
    children: [
      { path: 'list', component: UserListComponent },
      { path: 'add', component: UserAddComponent },
      { path: 'detail/:id', component: UserDetailComponent },
      { path: 'detail/:id/schedule', component: ScheduleComponent }
    ]
  },
  {
    path: 'patient',
    children: [
      { path: 'list', component: PatientListComponent },
      { path: 'add', component: PatientAddComponent },
      { path: 'detail/:id', component: PatientDetailComponent }
    ]
  },
  {
    path: 'consultation',
    children: [
      { path: 'list', component: ConsultationListComponent },
      { path: 'add', component: ConsultationAddComponent },
      { path: 'detail/:id', component: ConsultationDetailComponent },
      { path: 'observations/:id', component: ObservationsComponent },
      { path: 'list-upcoming', component: UpcomingConsultationListComponent },
      { path: 'list-patient', component: PatientConsultationListComponent }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
