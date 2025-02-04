import { Component, inject } from '@angular/core';
// import { RouterOutlet } from '@angular/router';
import { UserListComponent } from './user-list/user-list.component';
import { UsersService } from './service/users.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  imports: [UserListComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  userService = inject(UsersService)
  users: any = []
  groups: any = []
  schools: any = []

  constructor(){
    this.userService.getUsers().subscribe(val => {
      this.users = val
    })
    this.userService.getGroups().subscribe(val => {
      this.groups = val
    })
    this.userService.getSchools().subscribe(val => {
      this.schools = val
    })

  }
}
