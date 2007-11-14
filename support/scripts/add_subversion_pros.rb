$KCODE = 'u'
require "find"


# 对java文件以及html，xml文件加上 subversion的属性
# 
# @author: jun tsai
# @version $Revision$
# @since 0.8.5.1
# 

dir = File.dirname(__FILE__)+'/../../src'

Find.find(dir)  do |file|
      extname=File.extname(file)
      dirname=File.dirname(file);
      if(extname.eql?(".page")||extname.eql?(".jwc")||extname.eql?(".java")||extname.eql?(".html")||extname.eql?(".xml"))
        system("svn pd svn:keywords #{file}")
        system("svn ps svn:keywords 'Id Revision Date Author' #{file}")
      end
end

