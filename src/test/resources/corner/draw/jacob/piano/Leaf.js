/* 
 * 显示物
 */
Leaf=function()
{
  this.cornerWidth  = 15;
  this.cornerHeight = 15;

  Node.call(this);
  this.setDimension(150,100);
  this.originalHeight =-1;
}

/** base class of my example double click figure 
 * You can use circle, oval,.....too
 **/
Leaf.prototype = new Panel;
Leaf.prototype.type="Leaf";

/**
 * Create the Input&Output ports of the figure if the element will be assigned the first time
 * to the workflow/canvas.
 *
 **/
Leaf.prototype.setWorkflow=function(/*:Workflow*/ workflow)
{
  Node.prototype.setWorkflow.call(this,workflow);

  if(workflow!=null && this.inputPort==null)
  {
    this.inputPort = new InputPort();
    this.inputPort.setWorkflow(workflow);
    this.inputPort.setName("input");
    this.inputPort.setBackgroundColor(new  Color(115,115,245));
    this.addPort(this.inputPort,-5,this.height/2);
    
    this.outputPort = new OutputPort();
    this.outputPort.setMaxFanOut(5); // It is possible to add "5" Connector to this port
    this.outputPort.setWorkflow(workflow);
    this.outputPort.setName("output");
    this.outputPort.setBackgroundColor(new  Color(245,115,115));
    this.addPort(this.outputPort,this.width+5,this.height/2);
  }
}