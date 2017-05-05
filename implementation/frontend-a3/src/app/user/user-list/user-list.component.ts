import { Component, OnInit } from '@angular/core';

import { DialogService } from 'ng2-bootstrap-modal';
import { ConfirmComponent } from '../../dialogs/confirm/confirm.component';
import { InfoComponent } from '../../dialogs/info/info.component';

import { UserService } from '../../service/user.service';
import { User } from '../../model/user';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css'],
  providers:[
    UserService
  ]
})
export class UserListComponent implements OnInit {
  private users: User[];

  constructor(
    private userService: UserService,
    private dialogService: DialogService
  ) { }

  ngOnInit() {
    this.getUsersData();
  }

  private getUsersData():void {
    this.userService.getUsers().subscribe(users => this.users = users);
  }

  private delete(id: number): void {
    this.dialogService.addDialog(
      ConfirmComponent,
      {
        title: "Confirmation",
        message: "Are you sure that you want to delete user " + id + " ?"
      }
    ).subscribe(
      confirmation => {
        if (confirmation) {
          this.userService.deleteUser(id)
            .subscribe(
            book => {
              this.getUsersData();
              this.showMessage(
                "Success",
                "User " + id + " was sucessfully deleted",
                true
              )
            },
            err => {
              this.showMessage(
                "Error",
                "User " + id + " could not be deleted",
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
