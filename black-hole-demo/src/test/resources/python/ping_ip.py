#!/bin/python

import logging
import commands
import sys

logger = logging.getLogger()


def run_cmd(cmd):
    logger.debug("run_cmd: %s" % cmd)
    s, r = commands.getstatusoutput(cmd)
    logger.info(cmd)
    logger.info(r)
    if s > 0:
        logger.error("error,run cmd: %s, cause: %s, exit %d" % (cmd, r, s))
    return s, r


if __name__ == "__main__":
    s1, zk1_ip = run_cmd("ping  -c 1 -W 2 %s | grep PING | awk '{print $3}'" % 'ifarmshop.com')
    print '[EXECUTE_RESULT]{\"s1\": %s, \"msg\": %s}' % (s1, zk1_ip)