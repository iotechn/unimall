const getCodeOfRandom = () => {
	// 所需随机抽取的样本数组
	const nums = new Array("q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
	// 初始化 拼接字符串
	let str = "";
	for (let i = 0; i < 4; i++) {
		// 每次生成一个0 - 61 之间的 number 作为随机获取验证码的下标
		const p = Math.floor(Math.random() * 1000) % 36;
		// 拼接验证码  随机抽取大小写字母和数字
		str += nums[p];
	}
	return str;
};

export default [
	{
		url: "/Index/getCaptchaCode",
		method: "post",
		response: () => {
			return {
				status: {
					error_code: 0,
					error_msg: "success"
				},
				obj: {
					code_key: "6012e9ba65b442d2e5e7fec6e8eabcfd",
					code: getCodeOfRandom()
				}
			};
		}
	},
	{
		url: "/Index/login",
		method: "post",
		response: () => {
			return {
				status: {
					error_code: 0,
					error_msg: "success"
				},
				obj: {
					sys_token: "d33a7fdf547d2a086a96f4d38253cbc9",
					admin_nick_name: "程序员三千",
					admin_id: "1",
					avatar: "https://osstest.eetop.com/bewt365/578d0d88e7ad2f9ae99f10eee8e08d9c.jpg"
				}
			};
		}
	},
	{
		url: "/Index/getPermission",
		method: "post",
		response: () => {
			return {
				status: {
					error_code: 0,
					error_msg: "success"
				},
				obj: [
					{
						id: "1",
						name: "权限管理",
						parent_id: "0",
						type: "1",
						url: "/adminAuth/getRoleList",
						icon: "permission",
						children: [
							{
								id: "1-1",
								name: "角色列表",
								parent_id: "1",
								type: "2",
								url: "/adminAuth/getRoleList",
								icon: "permission",

								buttonList: [
									{
										id: "1-1-1",
										name: "查询",
										parent_id: "2",
										type: "3",
										url: "/adminAuth/getRoleList"
									},
									{
										id: "1-1-2",
										name: "配置权限",
										parent_id: "2",
										type: "3",
										url: "/adminAuth/setting"
									}
								]
							},
							{
								id: "1-2",
								name: "账号列表",
								parent_id: "1",
								icon: "permission",
								type: "2",
								url: "/adminAuth/adminList",
								buttonList: [
									{
										id: "1-2-1",
										name: "查询",
										parent_id: "1-2",
										type: "3",
										url: "/adminAuth/list"
									},
									{
										id: "1-2-2",
										name: "查看",
										parent_id: "1-2",
										type: "3",
										url: "/adminAuth/look"
									},
									{
										id: "1-2-3",
										name: "角色",
										parent_id: "1-2",
										type: "3",
										url: "/adminAuth/role"
									},
									{
										id: "1-2-4",
										name: "删除",
										parent_id: "1-2",
										type: "3",
										url: "/adminAuth/del"
									}
								]
							},
							{
								id: "1-3",
								name: "权限列表",
								icon: "permission",
								parent_id: "1",
								type: "2",
								url: "/adminAuth/permissionList"

							},
							{
								id: "1-4",
								name: "账号详情",
								icon: "permission",
								parent_id: "1",
								type: "2",
								url: "/account/detail",
								hidden: true

							}
						]
					},
					{
						id: "2",
						name: "三方库管理",
						parent_id: "0",
						type: "1",
						url: "/third/editor",
						icon: "article",
						children: [
							{
								id: "2-1",
								name: "富文本",
								parent_id: "2",
								type: "2",
								url: "/third/editor",
								icon: "permission",
								buttonList: [
									{
										id: "2-1-1",
										name: "查询",
										parent_id: "2-1",
										type: "3",
										url: "/third/editor"
									}
								]
							},
							{
								id: "2-2",
								name: "markdown",
								parent_id: "2",
								type: "2",
								url: "/third/markdown",
								icon: "permission",
								buttonList: [
									{
										id: "2-2-1",
										name: "查询",
										parent_id: "2-2",
										type: "3",
										url: "/third/markdown"
									}

								]
							}
						]
					},
					{
						id: "3",
						name: "音频可视化",
						icon: "role",
						parent_id: "0",
						type: "1",
						url: "/audio/canvas",
						children: [
							{
								id: "3-1",
								name: "canvas+audio",
								parent_id: "3",
								icon: "permission",
								type: "2",
								url: "/audio/canvas"

							},
							{
								id: "3-2",
								name: "wavesurfer",
								icon: "permission",
								parent_id: "3",
								type: "2",
								url: "/audio/wavesurfer"

							}

						]
					},
					{
						id: "4",
						name: "CSS动画",
						icon: "article-ranking",
						parent_id: "0",
						type: "1",
						url: "/cssAnimation/shootingStar",
						children: [
							{
								id: "4-100",
								name: "hover动画效果",
								icon: "home",
								parent_id: "3",
								type: "2",
								url: "/cssAnimation/hover",
								children: [
									{
										id: "4-5",
										name: "会发光的按钮",
										parent_id: "3",
										type: "2",
										icon: "permission",

										url: "/hover/hoverShiningBtn"

									},
									{
										id: "4-7",
										name: "菜单填充效果",
										parent_id: "3",
										type: "2",
										icon: "permission",

										url: "/hover/hoverFillText"

									},
									{
										id: "4-12",
										name: "菜单扫描效果",
										parent_id: "3",
										type: "2",
										icon: "permission",

										url: "/hover/hoverSlideMenu"
									},
									{
										id: "4-14",
										name: "border流动按钮",
										parent_id: "3",
										type: "2",
										icon: "permission",

										url: "/hover/hoverBorderBtn"
									}
								]
							},
							{
								id: "4-1",
								name: "流星雨",
								parent_id: "3",
								type: "2",
								icon: "permission",

								url: "/cssAnimation/shootingStar"

							},
							{
								id: "4-2",
								name: "能跳动的icon",
								parent_id: "3",
								icon: "permission",

								type: "2",
								url: "/cssAnimation/jumpBlock"

							},
							{
								id: "4-3",
								name: "奇怪的下载按钮",
								parent_id: "3",
								icon: "permission",

								type: "2",
								url: "/cssAnimation/downBtn"

							},
							{
								id: "4-4",
								name: "以视频为背景的文字",
								parent_id: "3",
								type: "2",
								icon: "permission",

								url: "/cssAnimation/videoMaskText"

							},
							{
								id: "4-6",
								name: "翻卡片动画",
								parent_id: "3",
								icon: "permission",
								type: "2",
								url: "/cssAnimation/filpCard"

							},
							{
								id: "4-8",
								name: "图片无缝滚动切换",
								parent_id: "3",
								type: "2",
								icon: "permission",

								url: "/cssAnimation/slidePic"

							},
							{
								id: "4-9",
								name: "ios动态气泡壁纸",
								parent_id: "3",
								type: "2",
								icon: "permission",

								url: "/cssAnimation/bubbleFloat"

							},
							{
								id: "4-10",
								name: "水波纹loading效果",
								parent_id: "3",
								type: "2",
								icon: "permission",

								url: "/cssAnimation/waveloading"
							},
							{
								id: "4-11",
								name: "全屏菜单动画",
								parent_id: "3",
								type: "2",
								icon: "permission",
								url: "/cssAnimation/fullscreenMenu"
							},
							{
								id: "4-13",
								name: "有趣的tab效果",
								parent_id: "3",
								type: "2",
								icon: "permission",
								url: "/cssAnimation/tabs"
							},
							{
								id: "4-14",
								name: "时钟效果",
								parent_id: "3",
								type: "2",
								icon: "permission",
								url: "/cssAnimation/clock"
							},
							{
								id: "4-15",
								name: "雪地划痕",
								parent_id: "3",
								type: "2",
								icon: "permission",
								url: "/cssAnimation/snowScratch"
							}

						]
					},
					{
						id: "5",
						name: "vueUse",
						parent_id: "0",
						type: "1",
						icon: "personnel",

						url: "/vueUse/elements",
						children: [
							{
								id: "5-1",
								name: "elements",
								parent_id: "3",
								icon: "permission",
								type: "2",
								url: "/vueUse/elements",
								children: [
									{
										id: "5-1-1",
										name: "useDraggable",
										parent_id: "3",
										type: "2",
										icon: "permission",
										url: "/elements/useDraggable"

									},
									{
										id: "5-1-2",
										name: "useDropZone",
										parent_id: "3",
										type: "2",
										icon: "permission",
										url: "/elements/useDropZone"

									},
									{
										id: "5-1-3",
										name: "useIntersectionObserver",
										parent_id: "3",
										type: "2",
										icon: "permission",
										url: "/elements/useIntersectionObserver"

									}
								]
							},
							{
								id: "5-2",
								name: "component",
								parent_id: "3",
								icon: "permission",
								type: "2",
								url: "/vueUse/component",
								children: [
									{
										id: "5-2-1",
										name: "createReusableTemplate",
										parent_id: "3",
										type: "2",
										icon: "permission",
										url: "/component/createReusableTemplate"

									}

								]
							}

						]
					}

				]
			};
		}
	},
	{
		url: "/adminAuth/adminList",
		method: "post",
		response: () => {
			return {
				status: {
					error_code: 0,
					error_msg: "success"
				},
				obj: [
					{
						id: "1",
						account: "admin",
						date: "2023-03-27 13:58:15",
						avatar: "https://res.lgdsunday.club/default-avatar.jpeg",
						role_name: "主管"
					},
					{
						id: "2",
						account: "程序员三千",
						date: "2023-01-27 13:58:15",
						avatar: "https://osstest.eetop.com/bewt365/578d0d88e7ad2f9ae99f10eee8e08d9c.jpg",
						role_name: "主管"
					},
					{
						id: "3",
						account: "张三",
						date: "2022-02-27 13:58:15",
						avatar: "https://res.lgdsunday.club/zhang-san.jpg",
						role_name: "普通员工"
					},
					{
						id: "4",
						account: "李四",
						date: "2022-02-27 13:58:15",
						avatar: "https://res.lgdsunday.club/default-avatar.jpeg",
						role_name: "普通员工"
					},
					{
						id: "7",
						account: "王五",
						date: "2022-02-27 13:58:15",
						avatar: "https://res.lgdsunday.club/default-avatar.jpeg",
						role_name: "普通员工"
					},
					{
						id: "8",
						account: "吴亦凡",
						date: "2022-02-27 13:58:15",
						avatar: "https://res.lgdsunday.club/default-avatar.jpeg",
						role_name: "普通员工"
					},
					{
						id: "9",
						account: "吴一帆",
						date: "2022-02-27 13:58:15",
						avatar: "https://res.lgdsunday.club/default-avatar.jpeg",
						role_name: "普通员工"
					},
					{
						id: "10",
						account: "张三丰",
						date: "2022-02-27 13:58:15",
						avatar: "https://res.lgdsunday.club/default-avatar.jpeg",
						role_name: "普通员工"
					}
				],
				page_info: {
					cur_page: "1",
					page_size: "10",
					total_items: "10"
				}
			};
		}
	},
	{
		url: "/adminAuth/getRoleList",
		method: "post",
		response: () => {
			return {
				status: {
					error_code: 0,
					error_msg: "success"
				},
				obj: [
					{
						id: "1",
						name: "主管",
						operator: "1686531954000"
					},
					{
						id: "2",
						name: "普通员工",
						operator: "1686532312000"
					},
					{
						id: "3",
						name: "销售",
						operator: "1686532312000"
					},
					{
						id: "4",
						name: "运营",
						operator: "1686532312000"
					},
					{
						id: "5",
						name: "产品经理",
						operator: "1686532312000"
					},
					{
						id: "6",
						name: "项目经理",
						operator: "1686532312000"
					}
				],
				page_info: {
					cur_page: "1",
					page_size: "10",
					total_items: "2"
				}
			};
		}
	}

];