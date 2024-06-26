import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicsListComponent } from './topics-list.component';

describe('TopicListComponent', () => {
  let component: TopicsListComponent;
  let fixture: ComponentFixture<TopicsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TopicsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopicsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
