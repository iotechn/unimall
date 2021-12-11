import config from '../../config'

const jweixin = require('./index')

const configWeiXin = async function(callback) {
	uni.request({
		url: config.baseUrl + '/m.api',
		data: {
			_gp: 'user',
			_mt: 'getH5Sign',
			url: window.location.href
		},
		method: 'POST',
		header: {
			'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
		},
		success: (res) => {
			if (res.statusCode === 200) {
				if (res.data.errno === 200) {
					let resConfig = res.data.data
					if (resConfig) {
					    let apiList = [ // 可能需要用到的能力
					        'chooseWXPay'
					    ];
					    let info = {
					        debug: config.debug, // 调试，发布的时候改为false
					        appId: config.h5Appid,
					        nonceStr: resConfig.noncestr,
					        timestamp: resConfig.timestamp,
					        signature: resConfig.sign,
					        jsApiList: apiList
					    };
					    jweixin.config(info);
					    jweixin.error(err => {
					        console.log('config fail:', err);
					    });
					
					    jweixin.ready(res => {
					        if (callback) callback(jweixin); // 配置成功
					    });
					}
				}
			}
		}
	})
 
}

export {
	configWeiXin
}