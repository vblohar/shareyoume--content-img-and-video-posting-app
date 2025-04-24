import { Component, EventEmitter, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { PostService } from '../../services/post.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-post',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-post.component.html',
  styleUrl: './create-post.component.css'
})

export class CreatePostComponent {
  @Output() postCreated = new EventEmitter<void>();
  postForm: FormGroup;
  selectedFile: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;

  constructor(
    private fb: FormBuilder,
    private postService: PostService
  ) {
    this.postForm = this.fb.group({
      content: ['', [Validators.required, Validators.maxLength(1000)]],
      media: [null]
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      
      // Create preview
      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result;
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit(): void {
    if (this.postForm.invalid) {
      return;
    }

    const formData = new FormData();
    formData.append('content', this.postForm.get('content')?.value);
    if (this.selectedFile) {
      formData.append('media', this.selectedFile);
    }

    this.postService.createPost(formData).subscribe({
      next: () => {
        this.postForm.reset();
        this.selectedFile = null;
        this.previewUrl = null;
        this.postCreated.emit();
      },
      error: (err) => {
        console.error('Error creating post:', err);
      }
    });
  }
}
