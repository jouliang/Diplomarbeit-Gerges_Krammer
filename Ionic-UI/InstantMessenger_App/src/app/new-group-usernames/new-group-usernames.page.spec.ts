import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewGroupUsernamesPage } from './new-group-usernames.page';

describe('NewGroupUsernamesPage', () => {
  let component: NewGroupUsernamesPage;
  let fixture: ComponentFixture<NewGroupUsernamesPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewGroupUsernamesPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewGroupUsernamesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
