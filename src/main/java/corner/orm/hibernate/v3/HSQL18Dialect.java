//==============================================================================
//file :       $Id$
//project:     corner
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	China Java Users Group http://cnjug.dev.java.net
//License:		the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.hibernate.v3;

import org.hibernate.dialect.HSQLDialect;

public class HSQL18Dialect extends HSQLDialect{

		public String[] getCreateSequenceStrings(String sequenceName) {
		    return new String[] {
		        "create table information_schema." + sequenceName
		            + " (zero integer)",
		        "insert into information_schema." + sequenceName + " values (0)",
		        "create sequence " + sequenceName + " start with 1"
		    };
		  }

		public String[] getDropSequenceStrings(String sequenceName) {
		    return new String[] {
		        "drop table information_schema." + sequenceName + " if exists",
		        "drop sequence " + sequenceName
		    };
		  }

		public String getSequenceNextValString(String sequenceName) {
		    return "select next value for " + sequenceName
		           + " from information_schema." + sequenceName;
		  }

		public String getQuerySequencesString() {
		    return "select sequence_name from information_schema.system_sequences";
		  }

		
}
