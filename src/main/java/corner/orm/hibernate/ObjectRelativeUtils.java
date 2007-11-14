// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id$
// created at:2005-10-18
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.hibernate;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;
import org.springframework.dao.DataAccessException;

import corner.util.PaginationBean;

/**
 * 支持O/R Mapping的相关的操作.
 * <p>
 * 所有的O/R Mapping操作都要实现该接口，该接口提供了通用的操作。
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai </a>
 * @version $Revision:3677 $
 * @since 2004-12-31
 */

public interface ObjectRelativeUtils {
    /*-------------------------------------------------------------------------
     * 对实体的查询操作，提供许多各式各样的查询方法。
     * ------------------------------------------------------------------------
     */

    /**
     * 执行一个查询，并返回一个列表.
     * @param 	query 查询语句.
     * @return 实例列表.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public List find(String query) throws DataAccessException;
    /**
     * 通过传递一个Pager对象来实现分段抓取数据。
     * @param query 查询语句
     * @param pager 分页对象
     * @return 分段的记录集
     * @throws DataAccessException 假如发生数据库操作错误.
     * @see Pager
     */
    public List find(String query,PaginationBean pager) throws DataAccessException;
    /**
     * 通过给定的第一条记录和抓取的记录数来获取一个分段数据.
     * @param query 查询语句.
     * @param first 第一条记录.
     * @param maxResults 抓取的记录数.
     * @return 分段的记录集.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public List find(String query,int first,int maxResults) throws DataAccessException;
    /**
     * 通过给定的第一条记录和抓取的记录数来获取一个分段数据.
     * @param query 查询语句.
     * @param param 参数
     * @param first 第一条记录.
     * 
     * @param maxResults 抓取的记录数.
     * @return 分段的记录集.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public List find(String query,Object parame,int first,int maxResults) throws DataAccessException;
    /**
     * 查询来获得记录的书目,假定此查询语句为一个获得记录集数目的语句.
     * @param query 查询语句,此语句一定是一个获得记录集数目的语句.
     * @return 记录集的数目.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public int count(String query) throws DataAccessException;
    
    /**
     * 通过一个语句来实现删除.
     * @param query 删除的语句.
     * @return 删除的数量.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public int delete(String query) throws DataAccessException;
  

    /*-------------------------------------------------------------------------
     * 对单一实体的搜索操作,他不同于load方法,主要是提供对各种属性的搜索操作.		   
     * ------------------------------------------------------------------------
     */
 
    /**
     * 通过entity的各种属性来进行查询.
     * @param entity 	 进行查询的实体.
     * @param first 	 第一条记录.
     * @param maxResults 抓取的记录数.
     * @return 查询结果的分段集.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
   // public <T> List<T> searchRecord(T entity,int first,int maxResults)throws DataAccessException;
    /**
     * 通过给定的entity和分页的bean来进行查询.
     * @param entity 进行查询的实体.
     * @param pager  分页使用的pager.
     * @return 查询的分段结果集.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
   // public <T> List<T> searchRecord(T entity,Pager pager) throws DataAccessException;
    /**
     * 查询结果集的个数.
     * @param entity 查询的实体.
     * @return 查询的分段结果集.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
  //  public <T> int countRecord(T entity) throws DataAccessException;
    /**
     * 通过Entity的各种属性来查询数据库看是否存在此记录.
     * @param entity 进行查询的实体.
     * @return 是否存在此记录.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    //public <T>boolean isExistRecord(T entity) throws DataAccessException;
    /**
     * 批量删除记录集.
     * <p>此方法根据实体的各种属性来进行对数据库的批量删除.可能会删掉多条记录.
     * @param entity 进行查询的实体.
     * @return 删除的记录数.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    //public <T>int deleteRecord(T entity) throws DataAccessException;
    /*-------------------------------------------------------------------------
     * 对单一实体的增,删,改操作.		   
     * ------------------------------------------------------------------------
     */
    
    /**
     * 装载一个实例化的类.
     * @param 	refClass	实例化类.
     * @param 	key 		主键.
     * @return 实例化的实例.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public <T> T load(Class<T> refClass, Serializable key) throws DataAccessException;
    /**
     * 装载一个实例化的类.
     * @param 	refClass	实例化类.
     * @param 	key 		主键.
     * @return 实例化的实例.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public <T> T get(Class<T> refClass, Serializable key) throws DataAccessException;
    /**
     * 保存一个实体.
     * @param obj 需要保存的实例.
     * @return 主键值.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public <T>Serializable save(T obj) throws DataAccessException;
    /**
     * 保存一个实体.
     * @param obj 需要保存的实例.
     * @param id primary key.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public <T> void save(T obj,Serializable id) throws DataAccessException;
    /**
     * 保存或者更新实体.
     * @param obj 需要保存或者更新的实体.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public <T>void saveOrUpdate(T obj) throws DataAccessException;
    /**
     * 更新实体.
     * @param obj 需要更新的实体.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public <T>void update(T obj) throws DataAccessException;
    /**
     * 删除实体.
     * @param obj 需要删除的实体.
     * @throws DataAccessException 假如发生数据库操作错误.
     */
    public <T> void delete(T obj) throws DataAccessException;
}