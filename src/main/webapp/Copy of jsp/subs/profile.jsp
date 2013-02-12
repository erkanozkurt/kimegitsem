<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>
<div class="contentDiv" id="contentDiv">
<s:actionmessage/>
<sx:tabbedpanel id="userProfile">
	<s:if test="#session.userContext.authenticatedUser.subscriberId==#request.subscriber.subscriberId">	
	       <sx:div id="profile" loadingText="Yükleniyor..." label="Profilim" theme="ajax" refreshOnShow="true" executeScripts="true" autoStart="false" >
					<table border="0" width="300px">
						<tr>
							<td width="30%">Adı</td>
							<td width="10%">:</td>
							<td width="*"><s:property value="#request.subscriber.name" />
							</td>
						</tr>
						<tr>
							<td width="30%">Soyadı</td>
							<td width="10%">:</td>
							<td width="*"><s:property value="#request.subscriber.surname" />
							</td>
						</tr>
						<tr>
							<td width="30%">Kayıt Tarihi</td>
							<td width="10%">:</td>
							<td width="*"><s:property value="#request.subscriber.joinDate"  />
							</td>
						</tr>
						<tr>
							<td width="30%">Cinsiyet</td>
							<td width="10%">:</td>
							<td width="*">
							<s:if test="%{#request.subscriber.gender='m'}">Erkek</s:if>
							<s:else>Kadın</s:else>
							</td>
						</tr>
					</table>
			</sx:div>
			<s:url id="friendsLink" action="friends?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
			<sx:div id="friends" labelposition="top" label="Arkadaşlarım" theme="ajax" href="%{friendsLink}" loadingText="Yükleniyor..." executeScripts="true" refreshOnShow="true"  autoStart="false">
			</sx:div>
			<s:url id="followLink" action="follow?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
			<sx:div id="follows" labelposition="top" label="Takip Ettiklerim" theme="ajax" href="%{followLink}" loadingText="Yükleniyor..." executeScripts="true" refreshOnShow="true" autoStart="false">
			</sx:div>
			<s:url id="inviteFriends" action="invite?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
			<sx:div id="invite" labelposition="top" label="Arkadaşlarını Davet Et" theme="ajax" href="%{inviteFriends}" loadingText="Yükleniyor..." executeScripts="true" refreshOnShow="true" autoStart="false">
			</sx:div>
			<s:url id="poisLink" action="pois?subscriberId=%{#request.subscriber.subscriberId}" namespace="/in"></s:url>
			<sx:div id="pois" labelposition="top" label="Hizmet Noktalarım" theme="ajax" href="%{poisLink}" loadingText="Yükleniyor..." executeScripts="true" refreshOnShow="true" autoStart="false">
			</sx:div>
			</s:if>
			<s:else>
				 <sx:div id="profile" loadingText="Yükleniyor..." label="Profil Bilgileri" theme="ajax" refreshOnShow="true" executeScripts="true" autoStart="false">
					
						<table border="0" width="300px">
							<tr>
								<td width="30%">Adı</td>
								<td width="10%">:</td>
								<td width="*"><s:property value="#request.subscriber.name" />
								</td>
							</tr>
							<tr>
								<td width="30%">Soyadı</td>
								<td width="10%">:</td>
								<td width="*"><s:property value="#request.subscriber.surname" />
								</td>
							</tr>
							<tr>
								<td width="30%">Kayıt Tarihi</td>
								<td width="10%">:</td>
								<td width="*"><s:property value="#request.subscriber.joinDate"  />
								</td>
							</tr>
							<tr>
								<td width="30%">Cinsiyet</td>
								<td width="10%">:</td>
								<td width="*">
								<s:if test="%{#request.subscriber.gender='m'}">Erkek</s:if>
								<s:else>Kadın</s:else>
								</td>
							</tr>
						</table>
				</sx:div>
				<s:url id="friendsLink" action="friends?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
				<sx:div id="friends" labelposition="top" label="Arkadaşları" theme="ajax" href="%{friendsLink}" loadingText="Yükleniyor..." executeScripts="true" refreshOnShow="true"  autoStart="false">
				</sx:div>
				<s:url id="followLink" action="follow?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
				<sx:div id="follows" labelposition="top" label="Takip Ettikleri" theme="ajax" href="%{followLink}" loadingText="Yükleniyor..." executeScripts="true" refreshOnShow="true" autoStart="false">
				</sx:div>
				<s:url id="poisLink" action="pois?subscriberId=%{#request.subscriber.subscriberId}" namespace="/in"></s:url>
				<sx:div id="pois" labelposition="top" label="Hizmet Noktaları" theme="ajax" href="%{poisLink}" loadingText="Yükleniyor..." executeScripts="true" refreshOnShow="true" autoStart="false">
				</sx:div>
			</s:else>

</sx:tabbedpanel>
</div>	