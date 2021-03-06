<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sj" uri="/struts-jquery-tags"%>
<div class="contentDiv" id="contentDiv">

<sj:tabbedpanel id="userProfile"  selectedTab="%{#request.st}">
	<s:if test="#request.subscriber eq null || #session.userContext.authenticatedUser.subscriberId==#request.subscriber.subscriberId">	
		   <sj:tab target="profile" id="profileTab" label="Profilim" tabindex="0"> </sj:tab>
	       <div id="profile">

					<table border="0" width="500px">
						<tr>
							<td>
								<img border="0" src="https://graph.facebook.com/<s:property value="#session.userContext.getAuthenticatedUser().getFacebookId()"/>/picture" width="65" length="65"/>
							</td>
							<td>
								<tr>
									<td width="10%">Adı</td>
									<td width="5%">:</td>
									<td width="*"><s:property value="#session.userContext.authenticatedUser.name" />
									</td>
								</tr>
								<tr>
									<td width="10%">Soyadı</td>
									<td width="5%">:</td>
									<td width="*"><s:property value="#session.userContext.authenticatedUser.surname" />
									</td>
								</tr>
								<tr>
									<td width="10%">Kayıt Tarihi</td>
									<td width="5%">:</td>
									<td width="*"><s:property value="#session.userContext.authenticatedUser.joinDate"  />
									</td>
								</tr>
								<tr>
									<td width="10%">Cinsiyet</td>
									<td width="5%">:</td>
									<td width="*">
									<s:if test="%{#session.userContext.authenticatedUser.gender='m'}">Erkek</s:if>
									<s:else>Kadın</s:else>
									</td>
								</tr>
							</td>
						</tr>
					</table>
			</div>
			<s:url id="friendsLink" action="friends?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
			<sj:tab id="friends" labelposition="top" label="Arkadaşlarım" href="%{friendsLink}" loadingText="Yükleniyor..." tabindex="1">
			</sj:tab>
			<s:url id="followLink" action="follow?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
			<sj:tab id="follows" labelposition="top" label="Takip Ettiklerim" href="%{followLink}" loadingText="Yükleniyor..." tabindex="2">
			</sj:tab>
			<s:url id="inviteFriends" action="invite?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
			<sj:tab id="invite" labelposition="top" label="Arkadaşlarını Davet Et" href="%{inviteFriends}" loadingText="Yükleniyor..." tabindex="3">
			</sj:tab>
			<s:url id="poisLink" action="pois?subscriberId=%{#request.subscriber.subscriberId}" namespace="/in"></s:url>
			<sj:tab id="pois" labelposition="top" label="Hizmet Noktalarım" href="%{poisLink}" loadingText="Yükleniyor..." tabindex="4">
			</sj:tab>
			
			<s:url id="messagesLink" action="messages" namespace="/subs"></s:url>
			<sj:tab id="messages" labelposition="top" label="Mesajlarım" href="%{messagesLink}" loadingText="Yükleniyor..." tabindex="5">
			</sj:tab>
			</s:if>
			<s:else>
				<sj:tab target="profile" id="profileTab" label="Profil Bilgileri"> </sj:tab>	
				 <div id="profile">
					
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
				</div>
				<s:url id="friendsLink" action="friends?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
				<sj:tab id="friends" labelposition="top" label="Arkadaşları" href="%{friendsLink}" loadingText="Yükleniyor...">
				</sj:tab>
				<s:url id="followLink" action="follow?profileId=%{#request.subscriber.subscriberId}" namespace="/subs"></s:url>
				<sj:tab id="follows" labelposition="top" label="Takip Ettikleri" href="%{followLink}" loadingText="Yükleniyor...">
				</sj:tab>
				<s:url id="poisLink" action="pois?subscriberId=%{#request.subscriber.subscriberId}" namespace="/in"></s:url>
				<sj:tab id="pois" labelposition="top" label="Hizmet Noktaları" href="%{poisLink}" loadingText="Yükleniyor...">
				</sj:tab>
				
			</s:else>

</sj:tabbedpanel>
<s:actionmessage/>
</div>	
