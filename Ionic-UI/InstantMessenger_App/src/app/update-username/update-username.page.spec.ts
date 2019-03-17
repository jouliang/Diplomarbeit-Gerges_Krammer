import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateUsernamePage } from './update-username.page';

describe('UpdateUsernamePage', () => {
  let component: UpdateUsernamePage;
  let fixture: ComponentFixture<UpdateUsernamePage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdateUsernamePage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateUsernamePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
