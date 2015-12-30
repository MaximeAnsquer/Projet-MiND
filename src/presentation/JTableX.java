package presentation;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class JTableX extends JTable{
	
	/*Cette classe étend JTable et nous permet de définir un TableCellEditor spécifique à chaque ligne (grâce
	 * à la classe RowEditorModel) et donc d'avoir des JCombobox différentes à chaque ligne, ce qui est impossible avec 
	 * l'Editor par défaut d'un JTable classique.
	 * 
	 */

	
	 protected RowEditorModel rm;
	 
	    public JTableX()
	    {
	          super();
	        rm = null;
	      }
	 
	      public JTableX(TableModel tm)
	      {
	          super(tm);
	          rm = null;
	      }
	 
	      public JTableX(TableModel tm, TableColumnModel cm)
	      {
	          super(tm,cm);
	          rm = null;
	      }
	 
	      public JTableX(TableModel tm, TableColumnModel cm,
	       ListSelectionModel sm)
	      {
	          super(tm,cm,sm);
	          rm = null;
	      }
	 
	      public JTableX(int rows, int cols)
	      {
	          super(rows,cols);
	          rm = null;
	      }
	 
	      public JTableX(final Vector rowData, final Vector columnNames)
	      {
	          super(rowData, columnNames);
	          rm = null;
	      }
	 
	      public JTableX(final Object[][] rowData, final Object[] colNames)
	      {
	          super(rowData, colNames);
	          rm = null;
	      }
	 
	      // new constructor
	     public JTableX(TableModel tm, RowEditorModel rm)
	      {
	          super(tm,null,null);
	          this.rm = rm;
	      }
	 
	      public void setRowEditorModel(RowEditorModel rm)
	      {
	          this.rm = rm;
	      }
	 
	      public RowEditorModel getRowEditorModel()
	      {
	          return rm;
	      }
	 
	      public TableCellEditor getCellEditor(int row, int col)
	      {
	          TableCellEditor tmpEditor = null;
	          if (rm!=null&&this.getModel().getColumnCount()-col-1==1){
	        	  
	              tmpEditor = rm.getEditor(row);
	          
	              return tmpEditor;
	          }
	          else{return super.getCellEditor(row,col);
	      }
	      }
}
