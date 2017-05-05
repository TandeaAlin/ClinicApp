import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { UserListComponent } from './user/user-list/user-list.component';
import { UserAddComponent } from './user/user-add/user-add.component';
import { UserDetailComponent } from './user/user-detail/user-detail.component';
import { ScheduleComponent } from './user/schedule/schedule.component';

import { PatientListComponent } from './patient/patient-list/patient-list.component';
import { PatientAddComponent } from './patient/patient-add/patient-add.component';
import { PatientDetailComponent } from './patient/patient-detail/patient-detail.component';

const routes: Routes = [
  {
    path: '',
    children: []
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
