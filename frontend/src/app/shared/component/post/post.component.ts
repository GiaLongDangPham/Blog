import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { Post } from 'src/app/core/interface/post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent {

  @Input() post!: Post;
  @Input() canEdit!: boolean;
  @Output() isEditing = new EventEmitter();

  constructor(private router: Router) {}

  goToDetail(postId: number) {
    this.router.navigate(['./posts', postId]);
  }

  onEditClick(event: Event) {
    event.stopPropagation(); // chặn click lan lên thẻ cha
    this.isEditing.emit();
  }
}
