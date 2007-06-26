//==============================================================================
// file :       $Id$
// project:     corner
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.mulitupload;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import corner.demo.model.AbstractModel;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 2.3.7
 */

@Entity(name="TestOne")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TestOne extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TestMany> tms;

	/**
	 * @return Returns the bs.
	 */
	@OneToMany(mappedBy="TEST_ONE")
	@OnDelete(action=OnDeleteAction.CASCADE)
	public List<TestMany> getTms() {
		return tms;
	}

	/**
	 * @param bs The bs to set.
	 */
	public void setTms(List<TestMany> tms) {
		this.tms = tms;
	}
}
