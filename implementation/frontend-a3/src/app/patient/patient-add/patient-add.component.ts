import { Component, OnInit, ViewChild } from '@angular/core';

import { PatientFormComponent } from '../patient-form/patient-form.component';
import { PatientService } from '../../service/patient.service';


@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.css'],
  providers: [
    PatientService
  ]
})
export class PatientAddComponent implements OnInit {
  private patient: any = {};
  private error: boolean = true;
  private created: boolean = null;
  private message: string = null;
  @ViewChild(PatientFormComponent) private form: PatientFormComponent;

  constructor(
    private patientService: PatientService
  ) { }

  ngOnInit() {
  }

  onSubmit() {
    if (this.form.validateInputs() == true) {
      this.patientService.createPatient(this.patient)
        .subscribe(
        book => {
          this.created = true;
          this.message = "The user was added sucessfully";
        },
        err => {
          this.created = false;
          if (err.status == 400) {
            this.message = "ERROR:" + err._body;
          } else {
            this.message = "ERROR: The new user could not be added";
          }
        });
    } else {
      this.created = false;
      this.message = "Error: Invalid data";
    }
  }

  processEvent(event): void {
    this.error = event;
  }

}
