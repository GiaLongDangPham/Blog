import { Pipe, PipeTransform } from '@angular/core';
import { UserService } from '../service/user.service';
import { filter, map, Observable } from 'rxjs';

@Pipe({
  name: 'userIdToName',
})
export class UserIdToNamePipe implements PipeTransform {

  constructor(private userService: UserService) {}

  transform(id: string): Observable<string | undefined> {
    return this.userService.getUser(id).pipe(
      filter(user => user != null && user != undefined),
      map(user => { return user.fullname; })
    );
  }

}
