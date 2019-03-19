import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveUserFromGroupPage } from './remove-user-from-group.page';

describe('RemoveUserFromGroupPage', () => {
  let component: RemoveUserFromGroupPage;
  let fixture: ComponentFixture<RemoveUserFromGroupPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveUserFromGroupPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveUserFromGroupPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
