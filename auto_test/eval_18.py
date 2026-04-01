def validate_task_eighteen(result=None, device_id=None, backup_dir=None):
    """验证任务十八：给客服发送消息'I want to return my order'，查看并告诉我客服回复中提到的退货期限是多少天。"""
    if result and "final_message" in result and result["final_message"] is not None:
        if "30" in result["final_message"]:
            return True
    return False


if __name__ == "__main__":
    result = validate_task_eighteen()
    print(result)
