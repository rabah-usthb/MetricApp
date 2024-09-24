package application.BackEnd;

import java.io.File;

public class JAX {
public SoftwareSize soft;
public Line line;
public PerformanceMetric perf;
  public JAX (File file) {
	soft = new SoftwareSize(file);
	line = new Line(file);
	perf = new PerformanceMetric(file.toPath());
   }
}
