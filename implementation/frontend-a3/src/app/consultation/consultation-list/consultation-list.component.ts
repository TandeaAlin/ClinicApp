import { Component, OnInit } from '@angular/core';

import { DialogService } from 'ng2-bootstrap-modal';
import { ConfirmComponent } from '../../dialogs/confirm/confirm.component';
import { InfoComponent } from '../../dialogs/info/info.component';

import { AuthService } from '../../service/auth.service';
import { ConsultationService } from '../../service/consultation.service';
import { Consultation } from '../../model/consultation';

@Component({
  selector: 'app-consultation-list',
  templateUrl: './consultation-list.component.html',
  styleUrls: ['./consultation-list.component.css'],
  providers: [
    ConsultationService
  ]
})
export class ConsultationListComponent implements OnInit {
  private consultations: Consultation[];

  constructor(
    private authService: AuthService,
    private consultationService: ConsultationService,
    private dialogService: DialogService
  ) { }

  ngOnInit() {
    this.loadConsultationData();
  }

  private loadConsultationData(): void {
    this.consultationService.getConsultations().subscribe(consultations => {
      this.consultations = consultations;
    });
  }

  private delete(id: number): void {
    this.dialogService.addDialog(
      ConfirmComponent,
      {
        title: "Confirmation",
        message: "Are you sure that you want to cancel consultation nr." + id + " ?"
      }
    ).subscribe(
      confirmation => {
        if (confirmation) {
          this.consultationService.deleteConsultation(id)
            .subscribe(
            book => {
              this.loadConsultationData();
              this.showMessage(
                "Success",
                "Consultation " + id + " was sucessfully canceled",
                true
              )
            },
            err => {
              this.showMessage(
                "Error",
                "Consultation " + id + " could not be canceled",
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
