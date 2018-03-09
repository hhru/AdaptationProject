package mocks;

import ru.hh.school.adaptation.hard.work.HardWorker;

public class HardWorkerMock extends HardWorker {
    @Override
    public void doWork() {
        System.out.println("I'm just a mock, i can't do any work");
    }
}
