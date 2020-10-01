import { Component } from '@angular/core';

@Component({
  selector: 'app-ng-for',
  templateUrl: './ng-for.component.html',
  styleUrls: ['./ng-for.component.css']
})
export class NgForComponent {

  courses: string[] = ['PHP', 'Java', 'Python', 'Javascript'];
  enabled: boolean = true;

  constructor() { }

  enableButton() {
    this.enabled ? this.enabled = false : this.enabled = true
  }

}
