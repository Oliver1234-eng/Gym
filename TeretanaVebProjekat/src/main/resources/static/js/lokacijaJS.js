$(document).ready(
				function() {
					$("#locales").change(
							function() {
								var selectedOption = $('#locales').val();
								console.log('Selected Option: ' + selectedOption);
								var locatin = window.location.href;
								console.log('Current Location: ' + locatin);
								var newLocatin = '';
								
								if (selectedOption != '') {
									var params = new window.URLSearchParams(window.location.search);
									var localeParam = params.get('locale');
									console.log('Locale Param: ' + localeParam);
									if(localeParam!=null){
										var positionStart = locatin.indexOf('locale=')+7;
										var positionEnd = positionStart+localeParam.length;
										var locatinStartStr = locatin.substring(0,positionStart);
										var locatinEndStr = locatin.substring(positionEnd);
										newLocatin = locatinStartStr + selectedOption +locatinEndStr;
										
									}else {
										if(locatin.indexOf('?')!=-1)
											newLocatin = locatin +'&locale='+ selectedOption;
										else 
											newLocatin = locatin +'/?locale='+ selectedOption;
									}
									
									window.location.replace(newLocatin);
								}
							});
					$("#podnozje").draggable();
				});