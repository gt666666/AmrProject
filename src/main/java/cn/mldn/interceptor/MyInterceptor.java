package cn.mldn.interceptor;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import cn.mldn.util.MessageUtil;
import cn.mldn.util.MimeValidator;
import cn.mldn.util.ValidatorRules;
public class MyInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 1、必须转型为HandlerMethod类的对象类型之后才可以知道操作的是那个Action，是那个具体的操作方法
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		System.out.println(handlerMethod);
		System.out.println(handlerMethod.getBean());
		// 2、拼凑要读取的资源文件中的key的内容
		String validatorKey = handlerMethod.getBeanType().getSimpleName() + "."
				+ handlerMethod.getMethod().getName() + ".rules";
		System.out.println(validatorKey);
		// 4、取得具体的验证规则
		String validateContent = MessageUtil.getMessage(
				handlerMethod.getBean(), validatorKey);
		if (validateContent == null || "".equals(validateContent)) { // 表示没有验证规则
			return true;
		} else {
			// 5、针对于每一个具体的操作进行验证处理

			String rules[] = validateContent.split("\\|"); // 实现的数据的拆分处理操作

			Map<String, String> result = new ValidatorRules(
					handlerMethod.getBean(), rules, request).validate();
			if (result.size() > 0) { // 有错误
				System.out.println(2222);
				request.setAttribute("errors", result);
				request.getRequestDispatcher("/errors.jsp").forward(request,
						response);
				return false;
			} else { // 随后进行上传文件是否合法的验证
				System.out.println(3333);
				boolean flag = MimeValidator.isMime(handlerMethod.getBean(),
						request); // 保存文件类型验证
				System.out.println(flag);
				if (flag == false) { // 验证失败
					result.put("file", MessageUtil.getMessage(
							handlerMethod.getBean(),
							"invalidate.file.mime.error.msg"));
					request.setAttribute("errors", result);
					request.getRequestDispatcher("/errors.jsp").forward(
							request, response);
					return false;
				}
			}
		}
		return true; // 如果不返回true表示截停，不在执行后续的控制层操作
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
