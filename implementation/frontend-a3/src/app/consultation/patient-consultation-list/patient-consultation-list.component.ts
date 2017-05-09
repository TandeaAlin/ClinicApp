import { Component, OnInit } from '@angular/core';

import { AuthService } from '../../service/auth.service';
import { ConsultationService } from '../../service/consultation.service';
import { Consultation } from '../../model/consultation';
import { PatientService } from '../../service/patient.service';
import { Patient } from '../../model/patient';

@Component({
  selector: 'app-patient-consultation-list',
  templateUrl: './patient-consultation-list.component.html',
  styleUrls: ['./patient-consultation-list.component.css'],
  providers: [
    ConsultationService,
    PatientService
  ]
})
export class PatientConsultationListComponent implements OnInit {
  private consultations: Consultation[];
  private patients: Patient[];
  private selectedPatient: Patient;
  private message: string;

  constructor(
    private consultationService: ConsultationService,
    private patientService: PatientService
  ) { }

  ngOnInit() {
    this.loadPatients();
  }

  private loadPatients(): void {
    this.patientService.getPatients().subscribe(patients => this.patients = patients);
  }

  private search(): void {
    if (this.selectedPatient == null) {
      this.message = "No patient selected";
    } else {
      this.consultationService.getConsultationsByPatient(this.selectedPatient)
        .subscribe(
        consultations => {
          if (consultations == null) {
            this.message = "No consultation could be found";
            this.consultations = null;
          } else {
            this.consultations = consultations;
            this.message = null;
          }
        },
        err => {
          this.message = "ERROR: No consultations found"
        }
        );
    }
  }

}
