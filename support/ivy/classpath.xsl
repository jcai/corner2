<!-- XSLT -->
<xsl:stylesheet version='1.0' xmlns:xsl='http://www.w3.org/1999/XSL/Transform' xmlns:str="http://exslt.org/strings" extension-element-prefixes="str" exclude-result-prefixes="str">
	<xsl:param name="classpath" />
	<xsl:template name="classpathentries">
		<xsl:for-each select='str:tokenize($classpath, " ")'>
			<classpathentry kind="lib">
				<xsl:attribute name="path">
					<xsl:value-of select='.' />
				</xsl:attribute>
			</classpathentry>

		</xsl:for-each>
	</xsl:template>
	<xsl:template match='/'>
		<classpath>
			<xsl:copy-of select="classpath/classpathentry[@kind!='lib']" />
			<xsl:call-template name="classpathentries" />
		</classpath>
	</xsl:template>
</xsl:stylesheet>

