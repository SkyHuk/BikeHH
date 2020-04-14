import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LayersheetComponent } from './layersheet.component';

describe('LayersheetComponent', () => {
  let component: LayersheetComponent;
  let fixture: ComponentFixture<LayersheetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LayersheetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LayersheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
