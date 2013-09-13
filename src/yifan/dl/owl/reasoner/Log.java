package yifan.dl.owl.reasoner;


public class Log
{
    private long now = System.currentTimeMillis();
    private long prec = 0L;
    
    public void write(String msg) {
	prec = now;
	now = System.currentTimeMillis();
	System.out.println(new StringBuilder("<time: ").append(now - prec)
			       .append
			       (" msec> ").append
			       (msg).append
			       ("\n").toString());
    }
}
