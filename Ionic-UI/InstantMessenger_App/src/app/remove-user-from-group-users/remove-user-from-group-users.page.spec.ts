import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RemoveUserFromGroupUsersPage } from './remove-user-from-group-users.page';

describe('RemoveUserFromGroupUsersPage', () => {
  let component: RemoveUserFromGroupUsersPage;
  let fixture: ComponentFixture<RemoveUserFromGroupUsersPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemoveUserFromGroupUsersPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RemoveUserFromGroupUsersPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
