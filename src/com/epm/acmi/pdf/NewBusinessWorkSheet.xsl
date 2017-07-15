<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:xdt="http://www.w3.org/2005/xpath-datatypes">
	<xsl:variable name="root" select="/Root"/>
	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="worksheet" page-width="210mm" page-height="290mm" margin-top="10mm">
					<fo:region-body margin-left="10mm" margin-right="10mm" margin-bottom="12mm"/>
					<fo:region-after extent="8mm"/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="worksheet">
				<fo:static-content flow-name="xsl-region-after" white-space-collapse="false">
					<fo:block start-indent="10mm" font-size="8pt">33-132 610 R12/03<fo:inline font-weight="bold" font-size="8pt">Page- <fo:page-number/>
						</fo:inline>
					</fo:block>
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body" font-size="8pt" white-space-collapse="false">
					<fo:block text-align="center" font-weight="bold" font-size="12pt" id="Title">
						<fo:block>NEW BUSINESS WORKSHEET</fo:block>
					</fo:block>
					<fo:table table-layout="fixed" space-before="10mm" display-align="after">
						<fo:table-column column-width="19mm"/>
						<fo:table-column column-width="46mm"/>
						<fo:table-column column-width="14mm"/>
						<fo:table-column column-width="26mm"/>
						<fo:table-column column-width="14mm"/>
						<fo:table-column column-width="26mm"/>
						<fo:table-column column-width="15mm"/>
						<fo:table-column column-width="25mm"/>
						<fo:table-body>
							<fo:table-row height="9mm">
								<fo:table-cell>
									<fo:block>Key Applicant</fo:block>
								</fo:table-cell>
								<fo:table-cell number-columns-spanned="3" text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="concat($root/KeyAppFirstName,' ',$root/KeyAppLastName)"/>
									</fo:block>
								</fo:table-cell>
								
								<fo:table-cell padding-left="2mm">
							    <xsl:choose>
									 <xsl:when test="count($root/PolicyNo) = 0">
										<fo:block>GFID #</fo:block>
									</xsl:when>	
									 <xsl:when test="count($root/PolicyNo) &gt; 0">
										<fo:block>Policy #</fo:block>
									</xsl:when>	
								</xsl:choose>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
										<fo:block>
											<xsl:choose>
												 <xsl:when test="count($root/PolicyNo) = 0">
													<xsl:value-of select="$root/gfid"/>
												</xsl:when>	
												 <xsl:when test="count($root/PolicyNo) &gt; 0">
													<xsl:value-of select="$root/PolicyNo"/>
												</xsl:when>	
											</xsl:choose>
										</fo:block>
									</fo:table-cell>
								<fo:table-cell padding-left="2mm">
									<fo:block>State:</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/State"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row height="9mm">
								<fo:table-cell>
									<fo:block>Agent Name</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/AgentName"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell padding-left="2mm">
									<fo:block>Agent #</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/AgentNumber"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell padding-left="2mm">
									<fo:block>EFT #</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/EFT"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell padding-left="2mm">
									<fo:block>List Bill #</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/ListBill"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:block start-indent="50mm" space-before="8mm">Previous File</fo:block>
					<fo:table table-layout="fixed" border-style="dotted" border-width="0.5pt">
						<fo:table-column column-width="20mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="18mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="17mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="22mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="49mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-header>
							<fo:table-row height="8mm" font-weight="bold">
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
									<fo:block space-before="2mm">Policy#</fo:block>
								</fo:table-cell>
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
									<fo:block space-before="2mm">Status</fo:block>
								</fo:table-cell>
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
									<fo:block space-before="2mm">PTD</fo:block>
								</fo:table-cell>
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
									<fo:block space-before="2mm">EFFDATE</fo:block>
								</fo:table-cell>
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
									<fo:block space-before="2mm">INSURED NAMES</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-header>
						<xsl:for-each select="$root/PrevPolicies/Policy">
							<fo:table-body display-align="center">
								<fo:table-row height="8mm">
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
										<fo:block>
											<xsl:value-of select="No"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
										<fo:block>
											<xsl:value-of select="Status"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
										<fo:block>
											<xsl:value-of select="PTD"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
										<fo:block>
											<xsl:value-of select="EFFDate"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" padding-left="2mm">
										<fo:block hyphenation-character="-" hyphenate="true" language="en">
											<xsl:value-of select="InsuredName"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</xsl:for-each>
					</fo:table>
					<xsl:if test="count($root/PrevPolicies/*)&gt;4">
						<fo:block>
							<fo:block>
								<fo:leader leader-pattern="rule" leader-length="185mm"/>
							</fo:block>
						</fo:block>
					</xsl:if>
					<xsl:if test="count($root/PrevPolicies/*)=4">
						<fo:block>
							<fo:block>
								<fo:leader leader-pattern="rule" leader-length="185mm"/>
							</fo:block>
						</fo:block>
					</xsl:if>
					<xsl:if test="count($root/PrevPolicies/*)=3">
						<fo:block space-before="6mm">
							<fo:block>
								<fo:leader leader-pattern="rule" leader-length="185mm"/>
							</fo:block>
						</fo:block>
					</xsl:if>
					<xsl:if test="count($root/PrevPolicies/*)=2">
						<fo:block space-before="14mm">
							<fo:block>
								<fo:leader leader-pattern="rule" leader-length="185mm"/>
							</fo:block>
						</fo:block>
					</xsl:if>
					<xsl:if test="count($root/PrevPolicies/*)=1">
						<fo:block space-before="22mm">
							<fo:block>
								<fo:leader leader-pattern="rule" leader-length="185mm"/>
							</fo:block>
						</fo:block>
					</xsl:if>
					<xsl:if test="count($root/PrevPolicies/*)=0">
						<fo:block space-before="38mm">
							<fo:block>
								<fo:leader leader-pattern="rule" leader-length="185mm"/>
							</fo:block>
						</fo:block>
					</xsl:if>
					<fo:block-container position="absolute" top="40mm" height="50mm" width="60mm" left="130mm" padding-left="10mm" id="Beside Tablel">
						<fo:table table-layout="fixed" text-align="left" display-align="after">
							<fo:table-column column-width="18mm"/>
							<fo:table-column column-width="37mm"/>
							<fo:table-body>
								<fo:table-row height="8mm">
									<fo:table-cell>
										<fo:block>Plan:</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" text-align="center">
										<fo:block>
											<xsl:value-of select="$root/Plan"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row height="8mm">
									<fo:table-cell>
										<fo:block>Network:</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" text-align="center">
										<fo:block>
											<xsl:value-of select="$root/Network"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row height="8mm">
									<fo:table-cell>
										<fo:block>Deductible:</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" text-align="center">
										<fo:block>
											<xsl:value-of select="$root/Network"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row height="8mm">
									<fo:table-cell>
										<fo:block>Co-Insurance:</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" text-align="center">
										<fo:block>
											<xsl:value-of select="$root/CoInsurance"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
								<fo:table-row height="8mm">
									<fo:table-cell>
										<fo:block>Co-Pay:</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt" text-align="center">
										<fo:block>
											<xsl:value-of select="$root/CoPay"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block-container>
					<fo:table table-layout="fixed" space-before="5mm" text-align="center" line-height="3mm" inline-progression-dimension="185mm">
						<fo:table-column column-width="92mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="93mm"/>
						<fo:table-header>
							<fo:table-row height="8mm">
								<fo:table-cell>
									<fo:block font-weight="bold">SHARED INFO</fo:block>
								</fo:table-cell>
								<fo:table-cell>
									<fo:block font-weight="bold">MEMOS &amp; AMENDMENTS </fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-header>
						<fo:table-body text-align="left">
							<fo:table-row>
								<fo:table-cell end-indent="6mm">
									<xsl:for-each select="$root/Notes/SharedNote">
										<fo:block line-height="9pt" start-indent="5mm"  space-after="2mm" hyphenation-character="-" hyphenate="true" language="en">
											<xsl:value-of select="concat(MemoNote,':  ',NoteDesc)"/>
										</fo:block>
									</xsl:for-each>
								</fo:table-cell>
								<fo:table-cell end-indent="6mm">
									<xsl:for-each select="$root/Notes/MemoAndAmendNote">
										<fo:block line-height="9pt" start-indent="5mm" space-after="2mm" hyphenation-character="-" hyphenate="true" language="en">
											<xsl:value-of select="concat(MemoNote,':  ',NoteDesc)"/>
										</fo:block>
									</xsl:for-each>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:table table-layout="fixed" space-before="5mm" display-align="after">
						<fo:table-column column-width="20mm"/>
						<fo:table-column column-width="32mm"/>
						<fo:table-column column-width="62mm"/>
						<fo:table-column column-width="71mm"/>
						<fo:table-body>
							<fo:table-row height="8mm">
								<fo:table-cell>
									<fo:block>Date Screened:</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/ScreenDate"/>
									</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="right">
									<fo:block>Screened By:</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/ScreenBy"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row height="8mm">
								<fo:table-cell>
									<fo:block>UW Initials:</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/UWInitial"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:block space-before="2mm">
						<fo:leader leader-pattern="rule" rule-style="dashed" leader-length="185mm" rule-thickness="1pt"/>
					</fo:block>
					<fo:table table-layout="fixed" display-align="after">
						<fo:table-column column-width="29mm"/>
						<fo:table-column column-width="32mm"/>
						<fo:table-column column-width="50mm"/>
						<fo:table-body>
							<fo:table-row height="8mm">
								<fo:table-cell>
									<fo:block>Med Point Queried By:</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>
										<xsl:value-of select="$root/PharmaRequestedBy"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
							<fo:table-row height="8mm">
								<fo:table-cell>
									<fo:block>Query Results:</fo:block>
								</fo:table-cell>
								<fo:table-cell text-align="center" border-after-style="dotted" border-after-width="0.5pt" number-columns-spanned="2">
									<fo:block>
										<xsl:value-of select="$root/NoResultsFound"/>
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:block space-before="2mm">
						<fo:leader leader-pattern="rule" rule-style="dashed" leader-length="185mm" rule-thickness="1pt"/>
					</fo:block>
					<fo:block text-decoration="underline" font-weight="bold" text-align="center" space-before="2mm">MEDICAL RECORDS REQUEST</fo:block>
					<fo:table table-layout="fixed" border-style="dotted" border-width="0.5pt" space-before="4mm" display-align="center" text-align="center">
						<fo:table-column column-width="30mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="40mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="55mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-column column-width="55mm" border-right-style="dotted" border-right-width="0.5pt"/>
						<fo:table-header keep-together="always">
							<fo:table-row font-weight="bold" height="5mm">
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>Date</fo:block>
								</fo:table-cell>
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>Applicant Type</fo:block>
								</fo:table-cell>
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>Applicant Name</fo:block>
								</fo:table-cell>
								<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
									<fo:block>Dr. Name</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</fo:table-header>
						<xsl:for-each select="$root/MedRecRequest/*">
							<fo:table-body>
								<fo:table-row height="5mm">
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
										<fo:block>
											<xsl:value-of select="RequestDate"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
										<fo:block>
											<xsl:value-of select="local-name()"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
										<fo:block>
											<xsl:value-of select="KeyName"/>
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border-after-style="dotted" border-after-width="0.5pt">
										<fo:block>
											<xsl:value-of select="Doctor"/>
										</fo:block>
									</fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</xsl:for-each>
					</fo:table>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>
