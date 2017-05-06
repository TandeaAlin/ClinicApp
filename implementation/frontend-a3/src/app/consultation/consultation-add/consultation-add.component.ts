import { Component, OnInit, ViewChild } from '@angular/core';

import { ConsultationFormComponent } from '../consultation-form/consultation-form.component';
import { ConsultationService } from '../../service/consultation.service';
import { Consultation } from '../../model/consultation';

@Component({
  selector: 'app-consultation-add',
  templateUrl: './consultation-add.component.html',
  styleUrls: ['./consultation-add.component.css'],
  providers:[
    ConsultationService
  ]
})
export class ConsultationAddComponent implements OnInit {

  private consultation: any = {};
  private error: boolean = true;
  private created: boolean = null;
  private message: string = null;
  @ViewChild(ConsultationFormComponent) private form: ConsultationFormComponent;

  constructor(
    private consultationService: ConsultationService
  ) { }

  ngOnInit() {
  }

  processEvent(event): void {
    this.error = event;
  }

  onSubmit() {
    if (this.form.validateInputs() == true) {
      this.consultationService.createConsultation(this.consultation)
        .subscribe(
        consultation => {
          this.created = true;
          this.message = "The consultation was created sucessfully";
        },
        err => {
          this.created = false;
          if (err.status == 400) {
            this.message = "ERROR:" + err._body;
          } else {
            this.message = "ERROR: The consultation could not be added";
          }
        });
    } else {
      this.created = false;
      this.message = "Error: Invalid data";
    }
  }
}
