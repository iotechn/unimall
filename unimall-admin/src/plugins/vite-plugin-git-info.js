// getGitInfo.js
import { execSync } from "child_process";

const BRANCH_COMMAND = "git rev-parse --abbrev-ref HEAD";// 获取分支名
const LAST_COMMIT_HASH_COMMAND = "git rev-parse HEAD";// 获取最后一次提交的 hash
const LAST_COMMIT_TIME_COMMAND = "git log -1 --pretty=format:%cd";// 获取最后一次提交的时间
const LAST_COMMIT_MSG_COMMAND = "git log -1 --pretty=format:%s"; // 获取最后一次提交的 message
const LAST_COMMIT_USER_COMMAND = "git log -1 --pretty=format:%an"; // 获取最后一次提交的提交者

// //执行git命令
const runGitCommand = async(command) => {
	try {
		const stdout = await execSync(command).toString().trim();
		return stdout;
	} catch (error) {
		console.error(`Failed to run git command: ${error}`);
		return "Failed to run git command";
	}
};

const getGitInfo = async() => {
	try {
		return {
			branch: await runGitCommand(BRANCH_COMMAND),
			lastCommitHash: await runGitCommand(LAST_COMMIT_HASH_COMMAND),
			lastCommitMsg: await runGitCommand(LAST_COMMIT_MSG_COMMAND),
			lastCommitTime: await runGitCommand(LAST_COMMIT_TIME_COMMAND),
			lastCommitUser: await runGitCommand(LAST_COMMIT_USER_COMMAND)
		};
	} catch (error) {
		console.error(`Failed to getGitInfo ${error}`);
	}
};

const plugin = () => {
	return {
		name: "vite-plugin-git-info",
		async transformIndexHtml(html) {
			const res = await getGitInfo();
			// 在 HTML 中插入一段 JavaScript 代码
			const scriptToAdd = JSON.stringify(res);
			// 在 <head> 标签里插入代码
			return [
				{
					attrs: { defer: true },
					children: `window._GIT_INFO=${scriptToAdd}`,
					inject: "head",
					tag: "script"
				}
			];
		}
	};
};

export default plugin;