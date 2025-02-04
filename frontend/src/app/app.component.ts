import { Component, inject, OnInit } from '@angular/core';
import { UsersService } from './service/users.service';
import { CommonModule } from '@angular/common';
import { Observable, forkJoin } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from './models/user.model';
import { Group } from './models/group.model';
import { School } from './models/school.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  userService = inject(UsersService);
  mappedUsers$: Observable<{ name: string; groupId: string; schoolName: string; }[]> | undefined;

  constructor() {}

  ngOnInit() {
    // Используем forkJoin для правильной загрузки всех данных
    forkJoin({
      users: this.userService.getUsers(),
      groups: this.userService.getGroups(),
      schools: this.userService.getSchools()
    }).subscribe(({ users, groups, schools }) => {
      // После получения всех данных, комбинируем их
      this.mappedUsers$ = new Observable((observer) => {
        observer.next(
          users.map((user: User) => {
            const group = groups.find((g: Group) => g.id === user.groupId);
            const school = group ? schools.find((s: School) => s.id === group.schoolId) : null;
      
            return {
              name: user.name,
              groupId: group ? group.name : 'N/A',
              schoolName: school ? school.name : 'N/A'
            };
          })
        );
        observer.complete();
      });
    });
  }

  trackUser(index: number, user: { name: string; groupId: string; schoolName: string }): string {
    return user.name; // Используем уникальное поле для отслеживания
  }
}