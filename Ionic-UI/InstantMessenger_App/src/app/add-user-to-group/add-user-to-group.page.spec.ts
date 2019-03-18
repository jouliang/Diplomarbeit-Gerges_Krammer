import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUserToGroupPage } from './add-user-to-group.page';

describe('AddUserToGroupPage', () => {
  let component: AddUserToGroupPage;
  let fixture: ComponentFixture<AddUserToGroupPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUserToGroupPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUserToGroupPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
