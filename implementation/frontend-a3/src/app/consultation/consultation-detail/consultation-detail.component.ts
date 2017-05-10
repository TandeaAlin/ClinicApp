import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DialogService } from "ng2-bootstrap-modal";
import { InfoComponent } from '../../dialogs/info/info.component';
import { InputComponent } from '../../dialogs/input/input.component';

import { ConsultationFormComponent } from '../consultation-form/consultation-form.component';
import { ConsultationService } from '../../service/consultation.service';
import { Consultation } from '../../model/consultation';
import { NotificationService } from '../../service/notification.service';
import { Notification } from '../../model/notification';

@Component({
  selector: 'app-consultation-detail',
  templateUrl: './consultation-detail.component.html',
  styleUrls: ['./consultation-detail.component.css'],
  providers: [
    ConsultationService,
    NotificationService
  ]
})
export class ConsultationDetailComponent implements OnInit {

  private consultation: Consultation;
  private error: boolean;
  private editMode: boolean = false;
  private message: string;
  private updated: boolean;
  @ViewChild(ConsultationFormComponent) private form: ConsultationFormComponent;

  constructor(
    private route: ActivatedRoute,
    private consultationService: ConsultationService,
    private notificationService: NotificationService,
    private dialogService: DialogService
  ) { }

  ngOnInit() {
    this.loadData();
  }

  private loadData(): void {
    this.route.params.subscribe(params => {
      let id = params['id'];

      this.consultationService.getConsultationById(id)
        .subscribe((consultation: Consultation) => this.consultation = consultation);
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
      this.consultationService.updateConsultation(this.consultation)
        .subscribe(
        book => {
          this.toggleMode();
          this.updated = true;
          this.message = "The consultation was edited sucessfully";
        },
        err => {
          this.updated = false;
          if (err.status == 400) {
            this.message = "ERROR:" + err._body;
          } else {
            this.message = "ERROR: The consultation could not be edited";
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

  private sendNotification(): void{
    this.notificationService.createNotificationForConsultation(this.consultation)
      .subscribe(
        notification => { this.showMessage("Success","The doctor was notified", true);},
        err => { this.showMessage("Error","The doctor could not be notified",false);}
      );
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
