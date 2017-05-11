import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DialogService } from "ng2-bootstrap-modal";
import { InfoComponent } from '../../dialogs/info/info.component';

import { ConsultationService } from '../../service/consultation.service';
import { Consultation, Observation } from '../../model/consultation';

@Component({
  selector: 'app-observations',
  templateUrl: './observations.component.html',
  styleUrls: ['./observations.component.css'],
  providers: [
    ConsultationService
  ]
})
export class ObservationsComponent implements OnInit {

  private consultation: Consultation;
  private newObservation: Observation;

  constructor(
    private route: ActivatedRoute,
    private consultationService: ConsultationService,
    private dialogService: DialogService
  ) {
    this.newObservation = new Observation();
    this.newObservation.id = null;
    this.newObservation.text = null;
  }

  ngOnInit() {
    this.loadData();
  }

  private loadData(): void {
    this.route.params.subscribe(params => {
      let id = params['id'];

      this.consultationService.getConsultationById(id)
        .subscribe((consultation: Consultation) => {
          this.consultation = consultation;
          console.log(this.consultation);
        });
    });
  }

  private addObservation(): void {
    this.newObservation.createdAt = new Date(Date.now());
    this.consultation.observations.push(this.newObservation);
    this.consultationService.updateConsultation(this.consultation).subscribe(
      consultation => {
        this.newObservation = new Observation();
        this.newObservation.id = null;
        this.newObservation.text = null;
        this.showMessage(
          "Sucess",
          "Observation added",
          true);
      },
      err => {
        this.showMessage(
          "Error",
          "This observation could not be added",
          false);
        console.log(err);
      });;
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
