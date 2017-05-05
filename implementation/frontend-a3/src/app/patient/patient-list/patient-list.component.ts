import { Component, OnInit } from '@angular/core';

import { DialogService } from 'ng2-bootstrap-modal';
import { ConfirmComponent } from '../../dialogs/confirm/confirm.component';
import { InfoComponent } from '../../dialogs/info/info.component';

import { PatientService } from '../../service/patient.service';
import { Patient } from '../../model/patient';

@Component({
  selector: 'app-patient-list',
  templateUrl: './patient-list.component.html',
  styleUrls: ['./patient-list.component.css'],
  providers: [
    PatientService
  ]
})
export class PatientListComponent implements OnInit {
  private patients: Patient[];

  constructor(
    private patientService: PatientService,
    private dialogService: DialogService
  ) { }

  ngOnInit() {
    this.loadPatientData();
  }

  private loadPatientData(): void {
    this.patientService.getPatients().subscribe((patients: Patient[]) => this.patients = patients)
  }

  private delete(id: number): void {
    this.dialogService.addDialog(
      ConfirmComponent,
      {
        title: "Confirmation",
        message: "Are you sure that you want to delete patient nr. " + id + " ?"
      }
    ).subscribe(
      confirmation => {
        if (confirmation) {
          this.patientService.deletePatient(id)
            .subscribe(
            book => {
              this.loadPatientData();
              this.showMessage(
                "Success",
                "Patient " + id + " was sucessfully deleted",
                true
              )
            },
            err => {
              this.showMessage(
                "Error",
                "Patient " + id + " could not be deleted",
                false);
            })
        }
      })
  }

  showMessage(title: string, message: string, success: boolean): void {
    this.dialogService.addDialog(
      InfoComponent,
      {
        title: title,
        message: message,
        success: success
      }
    );
  }
}
