import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { PatientFormComponent } from '../patient-form/patient-form.component';
import { PatientService } from '../../service/patient.service';
import { Patient } from '../../model/patient';

@Component({
  selector: 'app-patient-detail',
  templateUrl: './patient-detail.component.html',
  styleUrls: ['./patient-detail.component.css'],
  providers: [
    PatientService
  ]
})
export class PatientDetailComponent implements OnInit {

  private patient: Patient;
  private error: boolean;
  private editMode: boolean = false;
  private message: string;
  private updated: boolean;
  @ViewChild(PatientFormComponent) private form: PatientFormComponent;

  constructor(
    private route: ActivatedRoute,
    private patientService: PatientService
  ) { }

  ngOnInit() {
    this.loadData();
  }

  private loadData(): void {
    this.route.params.subscribe(params => {
      let id = params['id'];

      this.patientService.getPatientById(id)
        .subscribe((patient: Patient) => this.patient = patient);
    });
  }

  private toggleMode(): void {
    this.message = null;
    this.updated = null;
    this.editMode = !this.editMode;

    if (this.editMode == false) {
      this.loadData();
    }
  }

  onSubmit() {
    if (this.form.validateInputs() == true) {
      this.patientService.updatePatient(this.patient)
        .subscribe(
        book => {
          this.toggleMode();
          this.updated = true;
          this.message = "The patient was updated sucessfully";
        },
        err => {
          this.updated = false;
          if (err.status == 400) {
            this.message = "ERROR:" + err._body;
          } else {
            this.message = "ERROR: The patient could not be updated";
          }
        }
        )
    } else {
      this.updated = false;
      this.message = "Error: Invalid data";
    }
  }

  processEvent(event): void {
    this.error = event;
  }
}
