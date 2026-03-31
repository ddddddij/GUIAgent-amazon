# 任务9:立即购买一台白色的Nintendo Switch，用Amazon Gift Card结算。

import json
import os
import subprocess


def validate_task_nine(result=None, device_id=None, backup_dir=None):
    """验证任务九：立即购买一台白色的Nintendo Switch，用Amazon Gift Card结算。"""
    orders_file_path = os.path.join(backup_dir, "orders.json") if backup_dir else "orders.json"

    cmd = ["adb"]
    if device_id:
        cmd.extend(["-s", device_id])
    cmd.extend(["exec-out", "run-as", "com.example.amazon_sim", "cat", "files/orders.json"])
    subprocess.run(cmd, stdout=open(orders_file_path, "w"))

    try:
        with open(orders_file_path, "r", encoding="utf-8") as f:
            orders = json.load(f)
    except:
        return False

    if not isinstance(orders, list) or len(orders) == 0:
        return False

    first_order = orders[0]
    if not isinstance(first_order, dict):
        return False

    # 检查订单状态和支付方式
    if first_order.get("orderStatus") != "UNSHIPPED":
        return False
    if first_order.get("paymentMethod") != "Gift Card":
        return False

    # 检查订单商品中包含指定商品名
    items = first_order.get("items", [])
    for item in items:
        if isinstance(item, dict):
            if item.get("productName") == "Nintendo Switch OLED Model - White":
                return True

    return False


if __name__ == "__main__":
    result = validate_task_nine()
    print(result)
