package com.ti.signals;


import java.util.concurrent.ConcurrentLinkedQueue;

public class FinalSaveFilter extends SignalService{

//    private static final Logger LOG = LogManager.getLogger("DataLogger");

    private ConcurrentLinkedQueue<Number> list = new ConcurrentLinkedQueue<>();

//  Debug functional
    private String name = "";
    private boolean print = false;

    public FinalSaveFilter(String name) {
        this.name = name;
        print = true;
    }
//  Debug functional

    public FinalSaveFilter(){}

    @Override
    public void putElement(Number element) {
        list.add(element);
        if(print){
//            LOG.trace(name + "   "+ element.toString());
//            System.out.println(name +"  "+ list.size()+ "  " + element.toString());
        }
    }

    public ConcurrentLinkedQueue<Number> getResult(){
        return list;
    }
}
