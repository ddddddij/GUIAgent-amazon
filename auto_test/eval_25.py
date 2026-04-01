import json
import os
import subprocess


def validate_task_twenty_five(result=None, device_id=None, backup_dir=None):
    """验证任务二十五：立即购买首页打折最多的电子商品。"""
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

    if first_order.get("orderStatus") != "UNSHIPPED":
        return False

    items = first_order.get("items", [])
    for item in items:
        if isinstance(item, dict):
            if item.get("productId") == "prod_004":
                return True

    return False


if __name__ == "__main__":
    result = validate_task_twenty_five()
    print(result)
