module.exports = {
    // 可选类型
    types: [
      { value: 'feat', name: 'feat:    新增功能' },
      { value: 'fix', name: 'fix:      修复功能' },
      { value: 'docs', name: 'docs:     更新文档' },
      { value: 'style', name: 'style:    代码格式变更' },
      { value: 'refactor',name: 'refactor：     代码重构：非新增功能非修改功能' },
      { value: 'perf', name: 'perf:    性能优化' },
      { value: 'test', name: 'test:     增加测试用例' },
      { value: 'chore', name: 'chore:    构建过程或辅助工具的变动' },
      { value: 'revert', name: 'revert:   代码回退' },
    ],
    // 消息步骤
    messages: {
      type: '请选择提交类型:',
      customScope: '请输入修改范围(可选):',
      subject: '请简要描述提交(必填):',
      body: '请输入详细描述(可选):',
      footer: '请输入要关闭的issue(可选):',
      confirmCommit: '确认使用以上信息提交？(y/n/e/h)'
    },
    // 跳过问题
    skipQuestions: ['body', 'footer'],
    // subject文字长度默认是72
    subjectLimit: 72
  }