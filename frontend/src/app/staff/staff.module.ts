import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StaffComponent } from './staff.component';
import { PrimengModule } from '../primeng/primeng.module';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './component/feed/feed.component';
import { UserManagementComponent } from './component/user-management/user-management.component';
import { SharedModule } from '../shared/shared.module';
import { PostDetailsComponent } from '../shared/component/post-details/post-details.component';

const routes: Routes = [
  {
    path: '',
    component: FeedComponent
  },

  {
    path: 'user-management',
    component: UserManagementComponent
  },
  {
    path: 'posts', 
    children: [
      {
        path: ':postId',
        component: PostDetailsComponent
      }
    ]
  }
];


@NgModule({
  declarations: [
    StaffComponent,
    FeedComponent,
    UserManagementComponent,
  ],
  imports: [
    SharedModule,
    CommonModule,
    FormsModule,
    PrimengModule,
    RouterModule.forChild(routes)
  ]
})
export class StaffModule { }
