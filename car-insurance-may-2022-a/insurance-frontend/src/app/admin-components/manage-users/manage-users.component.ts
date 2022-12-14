import { Component, OnInit } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Role } from '../../model/role';
import { User } from '../../model/user';
import { UserService } from '../../services/user.service';
import { faEdit, faPlus, faTrash } from '@fortawesome/free-solid-svg-icons';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-manage-users',
  templateUrl: './manage-users.component.html',
  styleUrls: ['./manage-users.component.css'],
})
export class ManageUsersComponent implements OnInit {
  roles: Array<Role>;
  selectedRole: Role;

  users: Array<User>;

  userToCreate: User = {} as User;
  userToUpdate: User;
  userToDelete: User;
  userBackup: User = {} as User;

  faEdit = faEdit;
  faPlus = faPlus;
  faTrash = faTrash;

  constructor(
    private userService: UserService,
    private modalService: NgbModal,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((data) => (this.users = data));
    this.userService.getAllRoles().subscribe((data) => (this.roles = data));
  }

  openCreate(content: any) {
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 'lg',
      })
      .result.then(
        () => {
          this.userToCreate = {} as User;
        },
        () => {
          this.userToCreate = {} as User;
        }
      );
  }

  openUpdate(content: any, user: User) {
    this.userBackup = JSON.parse(JSON.stringify(user));
    this.userToUpdate = user;
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 'lg',
      })
      .result.then(
        (reason) => {
          if (reason != 'update') {
            this.resetUpdate();
          }
        },
        () => {
          this.resetUpdate();
        }
      );
  }

  openDelete(content: any, user: User) {
    this.userBackup = JSON.parse(JSON.stringify(user));
    this.userToDelete = user;
    this.modalService
      .open(content, {
        ariaLabelledBy: 'modal-basic-title',
        size: 's',
      })
      .result.then(
        () => {
          this.resetUpdate();
        },
        () => {
          this.resetUpdate();
        }
      );
  }

  update(modal: NgbModalRef) {
    this.userService.updateUser(this.userToUpdate).subscribe({
      next: () => {
        modal.close('update');
      },
      error: (err) => {
        this.snackBar.open(err.error.message, 'Ok', {
          panelClass: 'error-panel',
        });
        this.resetUpdate();
      },
      complete: () => {
        modal.close();
      },
    });
  }

  create(modal: NgbModalRef) {
    this.userService.createUser(this.userToCreate).subscribe({
      next: (resp) => {
        this.users.push(resp);
      },
      error: (err) => {
        this.snackBar.open(err.error.message, 'Ok', {
          panelClass: 'error-panel',
        });
        this.userToCreate = {} as User;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  deleteUser(modal: NgbModalRef) {
    this.userService.deleteUser(this.userToDelete.id).subscribe({
      next: (resp) => {
        this.users = this.users.filter(
          (user) => user.id !== this.userToDelete.id
        );
      },
      error: () => {
        this.userToDelete = {} as User;
      },
      complete: () => {
        modal.close();
      },
    });
  }

  resetUpdate() {
    this.userToUpdate = JSON.parse(JSON.stringify(this.userBackup));
    this.users = this.users.map((country) =>
      country.id === this.userToUpdate.id ? this.userToUpdate : country
    );
  }
}
